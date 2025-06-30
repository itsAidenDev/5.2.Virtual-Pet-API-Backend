package com.pet_api.virtual_pet.service;

import com.pet_api.virtual_pet.dto.ActionResultDTO;
import com.pet_api.virtual_pet.dto.TalkResponseDTO;
import com.pet_api.virtual_pet.dto.VillagerDTO;
import com.pet_api.virtual_pet.exception.custom.VillagerNotFoundException;
import com.pet_api.virtual_pet.exception.custom.UnauthorizedAccessException;
import com.pet_api.virtual_pet.mapper.VillagerMapper;
import com.pet_api.virtual_pet.model.Villager;
import com.pet_api.virtual_pet.model.User;
import com.pet_api.virtual_pet.model.activities.CaughtBug;
import com.pet_api.virtual_pet.model.activities.CaughtFish;
import com.pet_api.virtual_pet.repository.CaughtBugRepository;
import com.pet_api.virtual_pet.repository.CaughtFishRepository;
import com.pet_api.virtual_pet.repository.UserRepository;
import com.pet_api.virtual_pet.repository.VillagerRepository;
import com.pet_api.virtual_pet.security.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VillagerService {

    private final VillagerRepository villagerRepository;
    private final VillagerMapper villagerMapper;
    private final AuthUtil authUtil;
    private final VillagerStateUpdater villagerStateUpdater;
    private final UserRepository userRepository;
    private final CaughtBugRepository caughtBugRepository;
    private final CaughtFishRepository caughtFishRepository;

    public VillagerDTO getVillagerById(Long id) {
        Villager villager = findAndUpdate(id);
        return villagerMapper.toDto(villager);
    }

    public List<VillagerDTO> getAllVillagers() {
        if (authUtil.isAdmin()) {
            return villagerRepository.findAll().stream().map(villagerMapper::toDto).toList();
        } else {
            var currentUser = authUtil.getCurrentUser();
            return villagerRepository.findByUser(currentUser).stream().map(villagerMapper::toDto).toList();
        }
    }

    @Transactional
    public VillagerDTO createVillagerForUser(VillagerDTO villagerDTO, User user) {
        Villager villager = villagerMapper.toEntity(villagerDTO);
        villager.setUser(user);

        // Establecer valores iniciales
        villager.setFriendshipLevel(0);
        villager.setHappiness(70);
        villager.setHunger(30);
        villager.setEnergy(80);
        villager.setHealthLevel(50); // SALUD INICIAL AL 50%
        villager.setLastSleep(LocalDateTime.now());

        Villager savedVillager = villagerRepository.save(villager);
        return villagerMapper.toDto(savedVillager);
    }

    @Transactional
    public VillagerDTO updateVillager(Long villagerId, VillagerDTO villagerDTO) {
        Villager villager = villagerRepository.findById(villagerId)
                .orElseThrow(() -> new RuntimeException("Villager not found"));

        User currentUser = authUtil.getCurrentUser();

        boolean isAdmin = currentUser.getRole().equals("ROLE_ADMIN");
        boolean isOwner = villager.getUser().getUsername().equals(currentUser.getUsername());

        if (!isAdmin && !isOwner) {
            throw new RuntimeException("Unauthorized to modify this villager");
        }

        // Solo se actualiza el nombre por ahora
        villager.setVillagerName(villagerDTO.getVillagerName());

        villagerRepository.save(villager);
        return villagerMapper.toDto(villager);
    }

    @Transactional
    public String deleteVillager(Long id) {
        User currentUser = authUtil.getCurrentUser();
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Villager villager = villagerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Villager not found"));

        if (!villager.getUser().equals(user)) {
            throw new RuntimeException("You don't own this villager");
        }

        String villagerName = villager.getVillagerName();

        // Delete all caught bugs by this villager
        List<CaughtBug> caughtBugs = caughtBugRepository.findByVillagerVillagerId(villager.getVillagerId());        caughtBugRepository.deleteAll(caughtBugs);

        // Delete all caught fish by this villager
        List<CaughtFish> caughtFish = caughtFishRepository.findByVillagerVillagerId(villager.getVillagerId());
        caughtFishRepository.deleteAll(caughtFish);

        // Delete the villager
        villagerRepository.delete(villager);

        return String.format("%s has been released and will be missed. All their collected items have been removed from your inventory.", villagerName);
    }

    @Transactional
    public ActionResultDTO sleep(Long id) {
        Villager villager = findAndUpdate(id);

        if (villager.getEnergy() >= 90) {
            return new ActionResultDTO("Your villager is not tired enough to sleep!", villager.getEnergy(), villager.getFriendshipLevel());
        }

        villager.setEnergy(100);
        villager.setLastSleep(LocalDateTime.now());
        villager.increaseHappiness(10);
        villagerRepository.save(villager);

        return new ActionResultDTO("Your villager had a good rest and feels refreshed!", 100, villager.getFriendshipLevel());
    }

    @Transactional
    public TalkResponseDTO talk(Long id) {
        Villager villager = findAndUpdate(id);
        String phrase = VillagerPhraseService.getRandomPhrase(villager.getPersonality().name());

        int friendshipGain = 1;
        if (villager.getHappiness() > 70) {
            friendshipGain = 2; // More friendship gain when happy
        }

        villager.increaseFriendship(friendshipGain);
        villager.increaseHappiness(3);
        villagerRepository.save(villager);

        return new TalkResponseDTO(phrase, friendshipGain, villager.getFriendshipLevel());
    }

    @Transactional
    public ActionResultDTO giveGift(Long villagerId) {
        Villager villager = villagerRepository.findById(villagerId)
                .orElseThrow(() -> new RuntimeException("Villager not found"));

        // Pequeño aumento de hambre por la emoción
        int newHunger = Math.min(100, villager.getHunger() + 5);
        villager.setHunger(newHunger);

        // Aumentar felicidad y amistad significativamente
        int newHappiness = Math.min(100, villager.getHappiness() + 20);
        villager.setHappiness(newHappiness);

        int newFriendship = Math.min(100, villager.getFriendshipLevel() + 10);
        villager.setFriendshipLevel(newFriendship);

        villagerRepository.save(villager);

        String message = villager.getVillagerName() + " is happy with the gift! They appreciate you! 🎁😊";

        return ActionResultDTO.builder()
                .message(message)
                .newEnergy(villager.getEnergy())
                .newFriendship(newFriendship)
                .build();
    }

    @Transactional
    public ActionResultDTO play(Long villagerId) {
        Villager villager = villagerRepository.findById(villagerId)
                .orElseThrow(() -> new RuntimeException("Villager not found"));

        // Verificar si tiene suficiente energía
        if (villager.getEnergy() < 20) {
            return ActionResultDTO.builder()
                    .message(villager.getVillagerName() + " is too tired to play. They need to rest! 😴")
                    .newEnergy(villager.getEnergy())
                    .newFriendship(villager.getFriendshipLevel())
                    .build();
        }

        // Verificar si está muy enfermo
        if (villager.getHealthLevel() < 20) {
            return ActionResultDTO.builder()
                    .message(villager.getVillagerName() + " feels too ill to play. He needs medical attention! 🏥")
                    .newEnergy(villager.getEnergy())
                    .newFriendship(villager.getFriendshipLevel())
                    .build();
        }

        // Reducir energía por jugar
        int newEnergy = Math.max(0, villager.getEnergy() - 20);
        villager.setEnergy(newEnergy);

        // Aumentar hambre por la actividad
        int newHunger = Math.min(100, villager.getHunger() + 15);
        villager.setHunger(newHunger);

        // Aumentar felicidad y amistad
        int newHappiness = Math.min(100, villager.getHappiness() + 15);
        villager.setHappiness(newHappiness);

        int newFriendship = Math.min(100, villager.getFriendshipLevel() + 5);
        villager.setFriendshipLevel(newFriendship);

        // AGREGAR: Posibilidad de reducir salud
        checkHealthReduction(villager);

        villagerRepository.save(villager);

        String healthWarning = "";
        if (villager.getHealthLevel() < 30) {
            healthWarning = "They don't seem to be feeling very well! 😷";
        }

        String message = villager.getVillagerName() + " had a lot of fun playing! Now he's even hungrier and a little tired 🎮😋" + healthWarning;

        return ActionResultDTO.builder()
                .message(message)
                .newEnergy(newEnergy)
                .newFriendship(newFriendship)
                .build();
    }

    @Transactional
    public ActionResultDTO feed(Long villagerId) {
        Villager villager = villagerRepository.findById(villagerId)
                .orElseThrow(() -> new RuntimeException("Villager not found"));

        // Reducir hambre (el aldeano se sacia)
        int newHunger = Math.max(0, villager.getHunger() - 30); // Reduce 30 puntos de hambre
        villager.setHunger(newHunger);

        // Reducir energía porque comer da sueño
        int newEnergy = Math.max(10, villager.getEnergy() - 15); // Reduce 15 puntos de energía
        villager.setEnergy(newEnergy);

        // Aumentar un poco la felicidad
        int newHappiness = Math.min(100, villager.getHappiness() + 10);
        villager.setHappiness(newHappiness);

        // Pequeño aumento en amistad
        int newFriendship = Math.min(100, villager.getFriendshipLevel() + 2);
        villager.setFriendshipLevel(newFriendship);

        villagerRepository.save(villager);

        String message;
        if (newHunger == 0) {
            message = villager.getVillagerName() + " is completely satisfied and a little sleepy! 😋😴";
        } else if (newHunger < 30) {
            message = villager.getVillagerName() + " is not hungry anymore, but he's getting sleepy 😊😴";
        } else {
            message = villager.getVillagerName() + " feels better, but is still a little hungry 🍽️";
        }

        return ActionResultDTO.builder()
                .message(message)
                .newEnergy(newEnergy)
                .newFriendship(newFriendship)
                .build();
    }

    public VillagerDTO createNewVillagerForUser(VillagerDTO villagerDTO, User user) {
        Villager villager = villagerMapper.toEntity(villagerDTO);
        villager.setUser(user);

        villager.setFriendshipLevel(0);
        villager.setHappiness(70);
        villager.setHunger(30);
        villager.setEnergy(80);
        villager.setHealthLevel(50); // SALUD INICIAL AL 50%
        villager.setLastSleep(LocalDateTime.now());

        Villager savedVillager = villagerRepository.save(villager);
        return villagerMapper.toDto(savedVillager);
    }

    public ActionResultDTO heal(Long villagerId) {
        Villager villager = villagerRepository.findById(villagerId)
                .orElseThrow(() -> new RuntimeException("Villager not found"));

        // Verificar si necesita curación
        if (villager.getHealthLevel() >= 90) {
            return ActionResultDTO.builder()
                    .message(villager.getVillagerName() + " is already very healthy! They don't need treatment. 💚")
                    .newEnergy(villager.getEnergy())
                    .newFriendship(villager.getFriendshipLevel())
                    .build();
        }

        // Curar al aldeano
        int newHealth = Math.min(100, villager.getHealthLevel() + 40); // Cura 40 puntos
        villager.setHealthLevel(newHealth);

        // Reducir un poco de energía por el tratamiento
        int newEnergy = Math.max(5, villager.getEnergy() - 10);
        villager.setEnergy(newEnergy);

        // Aumentar un poco la felicidad
        int newHappiness = Math.min(100, villager.getHappiness() + 15);
        villager.setHappiness(newHappiness);

        // Pequeño aumento en amistad por cuidarlo
        int newFriendship = Math.min(100, villager.getFriendshipLevel() + 3);
        villager.setFriendshipLevel(newFriendship);

        villagerRepository.save(villager);

        String message;
        if (newHealth >= 90) {
            message = villager.getVillagerName() + " feels completely healed and full of life! ✨💚";
        } else if (newHealth >= 70) {
            message = villager.getVillagerName() + " feels much better after the treatment! 😊💊";
        } else {
            message = villager.getVillagerName() + " is getting better, but still needs more care 🏥";
        }

        return ActionResultDTO.builder()
                .message(message)
                .newEnergy(newEnergy)
                .newFriendship(newFriendship)
                .build();
    }

    private void checkHealthReduction(Villager villager) {
        Random random = new Random();

        // 15% de probabilidad de que baje la salud en actividades
        if (random.nextInt(100) < 15) {
            int healthReduction = random.nextInt(10) + 5; // Entre 5-14 puntos
            int newHealth = Math.max(10, villager.getHealthLevel() - healthReduction);
            villager.setHealthLevel(newHealth);

            // Si la salud baja mucho, también afecta la felicidad
            if (newHealth < 30) {
                int newHappiness = Math.max(10, villager.getHappiness() - 10);
                villager.setHappiness(newHappiness);
            }
        }
    }

    private Villager findAndUpdate(Long id) {
        Villager villager = villagerRepository.findById(id)
                .orElseThrow(() -> new VillagerNotFoundException("Villager not found"));

        User currentUser = authUtil.getCurrentUser();
        if (!authUtil.isAdmin() && !villager.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new UnauthorizedAccessException("You are not authorized to access this villager");
        }

        villagerStateUpdater.update(villager);
        return villager;
    }
}
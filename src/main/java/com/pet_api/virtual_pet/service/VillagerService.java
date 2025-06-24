package com.pet_api.virtual_pet.service;

import com.pet_api.virtual_pet.dto.ActionResultDTO;
import com.pet_api.virtual_pet.dto.TalkResponseDTO;
import com.pet_api.virtual_pet.dto.VillagerDTO;
import com.pet_api.virtual_pet.exception.custom.VillagerNotFoundException;
import com.pet_api.virtual_pet.exception.custom.UnauthorizedAccessException;
import com.pet_api.virtual_pet.mapper.VillagerMapper;
import com.pet_api.virtual_pet.model.Villager;
import com.pet_api.virtual_pet.model.User;
import com.pet_api.virtual_pet.repository.VillagerRepository;
import com.pet_api.virtual_pet.security.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VillagerService {

    private final VillagerRepository villagerRepository;
    private final VillagerMapper villagerMapper;
    private final AuthUtil authUtil;

    /*public List<VillagerDTO> getPredefinedVillagers() throws IOException {
        List<Villager> villagers = readVillagersFromJson();
        List<VillagerDTO> villagerDtos = new ArrayList<>();

        for (Villager villager : villagers)  {
            VillagerDTO villagerDto = VillagerDTO.builder()
                    .name(villager.getVillagerName())
                    .animalType(villager.getAnimalType())
                    .personality(villager.getPersonality())
                    .build();
            villagerDtos.add(villagerDto);
        }
        return villagerDtos;
    }

    private List<Villager> readVillagersFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("villagers.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Villager.class));
    }

     */

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

    public VillagerDTO createVillagerForUser(VillagerDTO villagerDTO, User user) {
        Villager villager = villagerMapper.toEntity(villagerDTO);
        villager.setUser(user);
        Villager savedVillager = villagerRepository.save(villager);
        return villagerMapper.toDto(savedVillager);
    }


    public VillagerDTO updateVillager(Long id, VillagerDTO villagerDTO) {
        Villager villager = villagerMapper.toEntity(villagerDTO);
        villager.setVillagerId(id);
        Villager updatedVillager = villagerRepository.save(villager);
        return villagerMapper.toDto(updatedVillager);
    }

    public void deleteVillager(Long id) {
        Villager villager = villagerRepository.findById(id)
                .orElseThrow(() -> new VillagerNotFoundException("Villager not found"));

        User currentUser = authUtil.getCurrentUser();
        if(!authUtil.isAdmin() && !villager.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new UnauthorizedAccessException("You are not authorized to delete this villager");
        } else {
            villagerRepository.delete(villager);
        }
    }

    public ActionResultDTO sleep(Long id) {
        Villager v = findAndUpdate(id);
        v.setEnergy(100);
        villagerRepository.save(v);
        return new ActionResultDTO("Your villager has slept and recovered energy.", 100, v.getFriendshipLevel());
    }

    public TalkResponseDTO talk(Long id) {
        Villager v = findAndUpdate(id);
        String phrase = VillagerPhraseService.getRandomPhrase(String.valueOf(v.getPersonality()));

        int newFriendship = Math.min(v.getFriendshipLevel() + 1, 100);
        v.setFriendshipLevel(newFriendship);
        villagerRepository.save(v);

        return new TalkResponseDTO(phrase, 1, newFriendship);
    }

    public ActionResultDTO giveGift(Long id) {
        Villager v = findAndUpdate(id);
        v.setFriendshipLevel(Math.min(v.getFriendshipLevel() + 3, 100));
        villagerRepository.save(v);

        return new ActionResultDTO("You gave them a gift, they seem very happy!", 0, v.getFriendshipLevel());
    }

    public ActionResultDTO play(Long id) {
        Villager v = findAndUpdate(id);
        v.setFriendshipLevel(Math.min(v.getFriendshipLevel() + 5, 100));
        villagerRepository.save(v);

        return new ActionResultDTO("You played with your villager, they had a lot of fun!", 0, v.getFriendshipLevel());
    }

    private Villager findAndUpdate(Long id) {
        Villager v = villagerRepository.findById(id)
                .orElseThrow(() -> new VillagerNotFoundException("Villager not found"));
        //updater.update(v);
        return v;
    }
}


package com.pet_api.virtual_pet.service.activities;

import com.pet_api.virtual_pet.dto.activities.ActivityResultDTO;
import com.pet_api.virtual_pet.dto.activities.FishDTO;
import com.pet_api.virtual_pet.exception.custom.VillagerNotFoundException;
import com.pet_api.virtual_pet.mapper.FishMapper;
import com.pet_api.virtual_pet.model.activities.CaughtFish;
import com.pet_api.virtual_pet.model.Villager;
import com.pet_api.virtual_pet.model.activities.Fish;
import com.pet_api.virtual_pet.repository.activities.CaughtFishRepository;
import com.pet_api.virtual_pet.repository.activities.FishRepository;
import com.pet_api.virtual_pet.repository.VillagerRepository;
import com.pet_api.virtual_pet.service.MuseumService;
import com.pet_api.virtual_pet.model.museum.MuseumRecord;
import com.pet_api.virtual_pet.utils.Habitat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FishingService {

    private final FishRepository fishRepository;
    private final CaughtFishRepository caughtFishRepository;
    private final VillagerRepository villagerRepository;
    private final MuseumService museumService;
    private final FishMapper fishMapper;
    private final Random random = new Random();

    public List<FishDTO> getAllFish() {
        return fishRepository.findAll().stream()
                .map(fishMapper::toDto)
                .toList();
    }

    public List<FishDTO> getCaughtFish(Long villagerId) {
        return caughtFishRepository.findByVillagerVillagerId(villagerId).stream()
                .map(caughtFish -> {
                    FishDTO dto = fishMapper.toDto(caughtFish.getFish());
                    dto.setCaughtAt(caughtFish.getCaughtAt());
                    dto.setLocation(caughtFish.getLocation());
                    return dto;
                })
                .toList();
    }

    @Transactional
    public ActivityResultDTO attemptFishCatch(Long villagerId, String habitatName) {
        Villager villager = villagerRepository.findById(villagerId)
                .orElseThrow(() -> new VillagerNotFoundException("Villager not found"));

        if (villager.getEnergy() < 15) {
            return ActivityResultDTO.builder()
                    .success(false)
                    .message("Your villager is too tired to fish! Let them rest first.")
                    .build();
        }

        int newHunger = Math.min(100, villager.getHunger() + 10);
        villager.setHunger(newHunger);

        Habitat habitat;
        try {
            habitat = Habitat.valueOf(habitatName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ActivityResultDTO.builder()
                    .success(false)
                    .message("Invalid habitat: " + habitatName)
                    .build();
        }

        List<Fish> availableFish = fishRepository.findByHabitat(habitat);
        if (availableFish.isEmpty()) {
            return ActivityResultDTO.builder()
                    .success(false)
                    .message("No fish available in this habitat!")
                    .build();
        }

        Random random = new Random();
        if (random.nextInt(100) < 20) { // 20% of chance
            int healthReduction = random.nextInt(8) + 3; // Between 3-10 points
            int newHealth = Math.max(10, villager.getHealthLevel() - healthReduction);
            villager.setHealthLevel(newHealth);
        }

        // Consume energy (fishing takes more energy than bug catching)
        villager.decreaseEnergy(15);

        // Attempt to catch a fish
        Fish targetFish = availableFish.get(random.nextInt(availableFish.size()));
        double catchChance = calculateCatchChance(villager, targetFish);

        if (random.nextDouble() < catchChance) {
            // Success! Catch the fish
            CaughtFish caughtFish = CaughtFish.builder()
                    .villager(villager)
                    .fish(targetFish)
                    .location(habitat.getHabitatName())
                    .build();

            caughtFishRepository.save(caughtFish);

            // Register the discovery in the museum
            try {
                museumService.registerNewDiscovery(
                        villagerId,
                        MuseumRecord.RecordType.FISH,
                        targetFish.getFishId(),
                        habitat.getHabitatName()
                );
            } catch (Exception e) {
                // Log the error but don't fail the operation
                System.err.println("Error registering fish in museum: " + e.getMessage());
            }

            // Increase friendship and happiness
            int friendshipGain = calculateFriendshipGain(targetFish.getFishRarity());
            villager.increaseFriendship(friendshipGain);
            villager.increaseHappiness(7); // Slightly more happiness than bug catching

            villagerRepository.save(villager);

            FishDTO caughtFishDTO = fishMapper.toDto(targetFish);
            caughtFishDTO.setCaughtAt(caughtFish.getCaughtAt());
            caughtFishDTO.setLocation(caughtFish.getLocation());

            return ActivityResultDTO.builder()
                    .success(true)
                    .message("Great catch! You caught a " + targetFish.getFishName() + "!")
                    .caughtItem(caughtFishDTO)
                    .experienceGained(targetFish.getFishValue())
                    .friendshipGained(friendshipGain)
                    .build();
        } else {
            // Failed to catch
            villagerRepository.save(villager);
            return ActivityResultDTO.builder()
                    .success(false)
                    .message("The fish got away! Keep trying.")
                    .experienceGained(7) // Small consolation prize
                    .build();
        }
    }

    private double calculateCatchChance(Villager villager, Fish fish) {
        double baseChance = 0.5; // 50% base chance (slightly lower than bugs)
        double difficultyModifier = 1.0 - fish.getCatchDifficulty();
        double friendshipBonus = villager.getFriendshipLevel() * 0.002; // Up to 20% bonus at max friendship
        double energyPenalty = villager.getEnergy() < 30 ? 0.25 : 0; // 25% penalty if low energy

        return Math.max(0.1, Math.min(0.9, baseChance * difficultyModifier + friendshipBonus - energyPenalty));
    }

    private int calculateFriendshipGain(String rarity) {
        return switch (rarity.toLowerCase()) {
            case "common" -> 2;
            case "uncommon" -> 3;
            case "rare" -> 4;
            case "legendary" -> 6;
            default -> 2;
        };
    }
}
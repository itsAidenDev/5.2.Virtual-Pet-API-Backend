package com.pet_api.virtual_pet.service.activities;

import com.pet_api.virtual_pet.dto.activities.ActivityResultDTO;
import com.pet_api.virtual_pet.dto.activities.BugDTO;
import com.pet_api.virtual_pet.exception.custom.VillagerNotFoundException;
import com.pet_api.virtual_pet.mapper.BugMapper;
import com.pet_api.virtual_pet.model.activities.CaughtBug;
import com.pet_api.virtual_pet.model.Villager;
import com.pet_api.virtual_pet.model.activities.Bug;
import com.pet_api.virtual_pet.repository.activities.BugRepository;
import com.pet_api.virtual_pet.repository.activities.CaughtBugRepository;
import com.pet_api.virtual_pet.repository.VillagerRepository;
import com.pet_api.virtual_pet.utils.Habitat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BugCatchingService {

    private final BugRepository bugRepository;
    private final CaughtBugRepository caughtBugRepository;
    private final VillagerRepository villagerRepository;
    private final BugMapper bugMapper;
    private final Random random = new Random();

    public List<BugDTO> getAllBugs() {
        return bugRepository.findAll().stream()
                .map(bugMapper::toDto)
                .toList();
    }

    public List<BugDTO> getCaughtBugs(Long villagerId) {
        return caughtBugRepository.findByVillagerVillagerId(villagerId).stream()
                .map(caughtBug -> {
                    BugDTO dto = bugMapper.toDto(caughtBug.getBug());
                    dto.setCaughtAt(caughtBug.getCaughtAt());
                    dto.setLocation(caughtBug.getLocation());
                    return dto;
                })
                .toList();
    }

    @Transactional
    public ActivityResultDTO attemptBugCatch(Long villagerId, String habitatName) {
        Villager villager = villagerRepository.findById(villagerId)
                .orElseThrow(() -> new VillagerNotFoundException("Villager not found"));

        int newHunger = Math.min(100, villager.getHunger() + 10);
        villager.setHunger(newHunger);

        if (villager.getEnergy() < 10) {
            return ActivityResultDTO.builder()
                    .success(false)
                    .message("Your villager is too tired to catch bugs! Let them rest first.")
                    .build();
        }

        Habitat habitat;
        try {
            habitat = Habitat.valueOf(habitatName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ActivityResultDTO.builder()
                    .success(false)
                    .message("Invalid habitat: " + habitatName)
                    .build();
        }

        List<Bug> availableBugs = bugRepository.findByBugHabitat(habitat);
        if (availableBugs.isEmpty()) {
            return ActivityResultDTO.builder()
                    .success(false)
                    .message("No bugs available in this habitat!")
                    .build();
        }

        Random random = new Random();
        if (random.nextInt(100) < 20) { // 20% de probabilidad en actividades
            int healthReduction = random.nextInt(8) + 3; // Entre 3-10 puntos
            int newHealth = Math.max(10, villager.getHealthLevel() - healthReduction);
            villager.setHealthLevel(newHealth);
        }

        // Consume energy
        villager.decreaseEnergy(10);

        // Attempt to catch a bug
        Bug targetBug = availableBugs.get(random.nextInt(availableBugs.size()));
        double catchChance = calculateCatchChance(villager, targetBug);

        if (random.nextDouble() < catchChance) {
            // Success! Catch the bug
            CaughtBug caughtBug = CaughtBug.builder()
                    .villager(villager)
                    .bug(targetBug)
                    .location(habitat.getHabitatName())
                    .build();

            caughtBugRepository.save(caughtBug);

            // Increase friendship and happiness
            int friendshipGain = calculateFriendshipGain(targetBug.getBugRarity());
            villager.increaseFriendship(friendshipGain);
            villager.increaseHappiness(5);

            villagerRepository.save(villager);

            BugDTO caughtBugDTO = bugMapper.toDto(targetBug);
            caughtBugDTO.setCaughtAt(caughtBug.getCaughtAt());
            caughtBugDTO.setLocation(caughtBug.getLocation());

            return ActivityResultDTO.builder()
                    .success(true)
                    .message("Congratulations! You caught a " + targetBug.getBugName() + "!")
                    .caughtItem(caughtBugDTO)
                    .experienceGained(targetBug.getBugValue())
                    .friendshipGained(friendshipGain)
                    .build();
        } else {
            // Failed to catch
            villagerRepository.save(villager);
            return ActivityResultDTO.builder()
                    .success(false)
                    .message("The bug got away! Better luck next time.")
                    .experienceGained(5) // Small consolation prize
                    .build();
        }
    }

    private double calculateCatchChance(Villager villager, Bug bug) {
        double baseChance = 0.6; // 60% base chance
        double difficultyModifier = 1.0 - bug.getCatchDifficulty();
        double friendshipBonus = villager.getFriendshipLevel() * 0.002; // Up to 20% bonus at max friendship
        double energyPenalty = villager.getEnergy() < 30 ? 0.2 : 0; // 20% penalty if low energy

        return Math.max(0.1, Math.min(0.95, baseChance * difficultyModifier + friendshipBonus - energyPenalty));
    }

    private int calculateFriendshipGain(String rarity) {
        return switch (rarity.toLowerCase()) {
            case "common" -> 1;
            case "uncommon" -> 2;
            case "rare" -> 3;
            case "legendary" -> 5;
            default -> 1;
        };
    }
}
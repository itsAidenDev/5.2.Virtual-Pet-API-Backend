package com.pet_api.virtual_pet.service;

import com.pet_api.virtual_pet.dto.inventory.InventoryItemDTO;
import com.pet_api.virtual_pet.dto.inventory.InventoryResponseDTO;
import com.pet_api.virtual_pet.dto.inventory.InventoryStatsDTO;
import com.pet_api.virtual_pet.model.User;
import com.pet_api.virtual_pet.model.Villager;
import com.pet_api.virtual_pet.model.activities.Bug;
import com.pet_api.virtual_pet.model.activities.CaughtBug;
import com.pet_api.virtual_pet.model.activities.CaughtFish;
import com.pet_api.virtual_pet.model.activities.Fish;
import com.pet_api.virtual_pet.repository.*;
import com.pet_api.virtual_pet.repository.activities.BugRepository;
import com.pet_api.virtual_pet.repository.activities.CaughtBugRepository;
import com.pet_api.virtual_pet.repository.activities.CaughtFishRepository;
import com.pet_api.virtual_pet.repository.activities.FishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InventoryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VillagerRepository villagerRepository;

    @Autowired
    private CaughtBugRepository caughtBugRepository;

    @Autowired
    private CaughtFishRepository caughtFishRepository;

    @Autowired
    private BugRepository bugRepository;

    @Autowired
    private FishRepository fishRepository;

    public InventoryResponseDTO getUserInventory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Villager> userVillagers = villagerRepository.findByUser(user);

        List<InventoryItemDTO> inventoryItems = new ArrayList<>();

        // Get all caught bugs for user's villagers
        for (Villager villager : userVillagers) {
            List<CaughtBug> caughtBugs = caughtBugRepository.findByVillagerVillagerId(villager.getVillagerId());
            for (CaughtBug caughtBug : caughtBugs) {
                Bug bug = caughtBug.getBug();
                InventoryItemDTO item = new InventoryItemDTO();
                item.setId(generateItemId("BUG", caughtBug.getId()));
                item.setItemId(bug.getBugId());
                item.setItemName(bug.getBugName());
                item.setItemDescription(bug.getBugDescription());
                item.setItemType("BUG");
                item.setRarity(bug.getBugRarity());
                item.setValue(bug.getBugValue());
                item.setHabitat(String.valueOf(bug.getBugHabitat()));
                item.setCaughtAt(caughtBug.getCaughtAt());
                item.setCaughtBy(villager.getVillagerName());
                item.setLocation(caughtBug.getLocation());
                item.setQuantity(1); // Individual catches
                inventoryItems.add(item);
            }
        }

        // Get all caught fish for user's villagers
        for (Villager villager : userVillagers) {
            List<CaughtFish> caughtFish = caughtFishRepository.findByVillagerVillagerId(villager.getVillagerId());
            for (CaughtFish fish : caughtFish) {
                Fish fishData = fish.getFish();
                InventoryItemDTO item = new InventoryItemDTO();
                item.setId(generateItemId("FISH", fish.getId()));
                item.setItemId(fishData.getFishId());
                item.setItemName(fishData.getFishName());
                item.setItemDescription(fishData.getFishDescription());
                item.setItemType("FISH");
                item.setRarity(fishData.getFishRarity());
                item.setValue(fishData.getFishValue());
                item.setHabitat(String.valueOf(fishData.getHabitat()));
                item.setCaughtAt(fish.getCaughtAt());
                item.setCaughtBy(villager.getVillagerName());
                item.setLocation(fish.getLocation());
                item.setQuantity(1); // Individual catches
                inventoryItems.add(item);
            }
        }

        // Group identical items and sum quantities
        Map<String, InventoryItemDTO> groupedItems = new HashMap<>();
        for (InventoryItemDTO item : inventoryItems) {
            String key = item.getItemType() + "_" + item.getItemId();
            if (groupedItems.containsKey(key)) {
                InventoryItemDTO existing = groupedItems.get(key);
                existing.setQuantity(existing.getQuantity() + 1);
                // Keep the most recent catch date
                if (item.getCaughtAt().isAfter(existing.getCaughtAt())) {
                    existing.setCaughtAt(item.getCaughtAt());
                    existing.setCaughtBy(item.getCaughtBy());
                    existing.setLocation(item.getLocation());
                }
            } else {
                groupedItems.put(key, item);
            }
        }

        List<InventoryItemDTO> finalItems = new ArrayList<>(groupedItems.values());

        // Sort by most recent first
        finalItems.sort((a, b) -> b.getCaughtAt().compareTo(a.getCaughtAt()));

        InventoryStatsDTO stats = calculateInventoryStats(finalItems);

        InventoryResponseDTO response = new InventoryResponseDTO();
        response.setItems(finalItems);
        response.setStats(stats);

        return response;
    }

    public InventoryStatsDTO getInventoryStats(String username) {
        InventoryResponseDTO inventory = getUserInventory(username);
        return inventory.getStats();
    }

    public void deleteInventoryItem(String username, String itemId) {
        String[] parts = itemId.split("_");
        if (parts.length != 2) {
            throw new RuntimeException("Invalid item ID format");
        }

        String itemType = parts[0];
        Long dbId = Long.parseLong(parts[1]);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if ("BUG".equals(itemType)) {
            CaughtBug caughtBug = caughtBugRepository.findById(dbId)
                    .orElseThrow(() -> new RuntimeException("Bug not found"));

            // Verify ownership of the bug
            if (!caughtBug.getVillager().getUser().equals(user)) {
                throw new RuntimeException("You don't own this item");
            }

            caughtBugRepository.delete(caughtBug);
        } else if ("FISH".equals(itemType)) {
            CaughtFish caughtFish = caughtFishRepository.findById(dbId)
                    .orElseThrow(() -> new RuntimeException("Fish not found"));

            // Verify ownership of the fish
            if (!caughtFish.getVillager().getUser().equals(user)) {
                throw new RuntimeException("You don't own this item");
            }

            caughtFishRepository.delete(caughtFish);
        } else {
            throw new RuntimeException("Invalid item type");
        }
    }

    public String useInventoryItem(String username, String itemId) {

        String[] parts = itemId.split("_");
        if (parts.length != 2) {
            throw new RuntimeException("Invalid item ID format");
        }

        String itemType = parts[0];
        Long dbId = Long.parseLong(parts[1]);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Villager> userVillagers = villagerRepository.findByUser(user);
        if (userVillagers.isEmpty()) {
            throw new RuntimeException("No villagers found");
        }

        // Get a random villager to give the benefit to
        Villager randomVillager = userVillagers.get(new Random().nextInt(userVillagers.size()));

        int pointsGained = 0;
        String itemName = "";

        if ("BUG".equals(itemType)) {
            CaughtBug caughtBug = caughtBugRepository.findById(dbId)
                    .orElseThrow(() -> new RuntimeException("Bug not found"));

            if (!caughtBug.getVillager().getUser().equals(user)) {
                throw new RuntimeException("You don't own this item");
            }

            pointsGained = caughtBug.getBug().getBugValue() / 2; // Half the value as points
            itemName = caughtBug.getBug().getBugName();

            // Give happiness boost to villager
            randomVillager.setHappiness(Math.min(100, randomVillager.getHappiness() + 10));
            villagerRepository.save(randomVillager);

            caughtBugRepository.delete(caughtBug);

        } else if ("FISH".equals(itemType)) {
            CaughtFish caughtFish = caughtFishRepository.findById(dbId)
                    .orElseThrow(() -> new RuntimeException("Fish not found"));

            if (!caughtFish.getVillager().getUser().equals(user)) {
                throw new RuntimeException("You don't own this item");
            }

            pointsGained = caughtFish.getFish().getFishValue() / 2; // Half the value as points
            itemName = caughtFish.getFish().getFishName();

            // Give happiness boost to villager
            randomVillager.setHappiness(Math.min(100, randomVillager.getHappiness() + 10));
            villagerRepository.save(randomVillager);

            caughtFishRepository.delete(caughtFish);

        } else {
            throw new RuntimeException("Invalid item type");
        }

        return String.format("Used %s! %s gained %d happiness points and you earned %d points!",
                itemName, randomVillager.getVillagerName(), 10, pointsGained);
    }

    private InventoryStatsDTO calculateInventoryStats(List<InventoryItemDTO> items) {
        InventoryStatsDTO stats = new InventoryStatsDTO();

        int totalItems = items.stream().mapToInt(InventoryItemDTO::getQuantity).sum();
        int totalValue = items.stream().mapToInt(item -> item.getValue() * item.getQuantity()).sum();
        int uniqueSpecies = items.size();
        int rareItems = (int) items.stream()
                .filter(item -> "rare".equalsIgnoreCase(item.getRarity()) ||
                        "legendary".equalsIgnoreCase(item.getRarity()))
                .mapToInt(InventoryItemDTO::getQuantity)
                .sum();

        stats.setTotalItems(totalItems);
        stats.setTotalValue(totalValue);
        stats.setUniqueSpecies(uniqueSpecies);
        stats.setRareItems(rareItems);

        return stats;
    }

    private String generateItemId(String type, Long dbId) {
        return type + "_" + dbId;
    }

    public String sellInventoryItem(String username, String itemId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String[] parts = itemId.split("_");
        if (parts.length != 2) {
            throw new RuntimeException("Invalid item ID format");
        }
        String itemType = parts[0].toUpperCase();
        Long dbId;
        try {
            dbId = Long.parseLong(parts[1]);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid item ID format");
        }

        int pointsGained = 0;
        String itemName = "";

        if ("BUG".equals(itemType)) {
            CaughtBug caughtBug = caughtBugRepository.findById(dbId)
                    .orElseThrow(() -> new RuntimeException("Bug not found"));

            if (!caughtBug.getVillager().getUser().equals(user)) {
                throw new RuntimeException("You don't own this item");
            }

            pointsGained = caughtBug.getBug().getBugValue();
            itemName = caughtBug.getBug().getBugName();
            caughtBugRepository.delete(caughtBug);

        } else if ("FISH".equals(itemType)) {
            CaughtFish caughtFish = caughtFishRepository.findById(dbId)
                    .orElseThrow(() -> new RuntimeException("Fish not found"));

            if (!caughtFish.getVillager().getUser().equals(user)) {
                throw new RuntimeException("You don't own this item");
            }

            pointsGained = caughtFish.getFish().getFishValue();
            itemName = caughtFish.getFish().getFishName();
            caughtFishRepository.delete(caughtFish);

        } else {
            throw new RuntimeException("Invalid item type");
        }

        user.setPoints(user.getPoints() + pointsGained);
        userRepository.save(user);

        return String.format("Sold %s for %d points!", itemName, pointsGained);
    }
}


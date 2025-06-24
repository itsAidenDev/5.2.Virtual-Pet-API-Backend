package com.pet_api.virtual_pet.service;

import com.pet_api.virtual_pet.model.Villager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class VillagerStateUpdater {

    public void update(Villager villager) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime last = villager.getVillagerLastUpdated();
        long daysPassed = ChronoUnit.DAYS.between(last, today);
        if (daysPassed <= 0) return;

        // Update energy
        int newEnergy = villager.getEnergy() - (int)(daysPassed * 5);
        villager.setEnergy(Math.max(newEnergy, 0));

        // Update friendship if has been more than 7 days without interaction
        long weeksPassed = ChronoUnit.WEEKS.between(last, today);
        if (weeksPassed > 0) {
            int newFriendship = villager.getFriendshipLevel() - (int)(weeksPassed * 2);
            villager.setFriendshipLevel(Math.max(newFriendship, 0));
        }

        // Update last updated
        villager.setVillagerLastUpdated(today);
    }
}

package com.pet_api.virtual_pet.mapper;

import com.pet_api.virtual_pet.dto.VillagerDTO;
import com.pet_api.virtual_pet.model.Villager;
import org.springframework.stereotype.Component;

@Component
public class VillagerMapper {

    public VillagerDTO toDto(Villager villager) {
        return VillagerDTO.builder()
                .villagerId(villager.getVillagerId())
                .villagerName(villager.getVillagerName())
                .animalType(villager.getAnimalType())
                .personality(villager.getPersonality())
                .friendshipLevel(villager.getFriendshipLevel())
                .happiness(villager.getHappiness())
                .hunger(villager.getHunger())
                .energy(villager.getEnergy())
                .healthLevel(villager.getHealthLevel())
                .stacks(villager.getStacks())
                .lastSleep(villager.getLastSleep())
                .ownerUsername(villager.getUser() != null ? villager.getUser().getUsername() : null)
                .build();
    }

    public Villager toEntity(VillagerDTO villagerDTO) {
        return Villager.builder()
                .villagerId(villagerDTO.getVillagerId())
                .villagerName(villagerDTO.getVillagerName())
                .animalType(villagerDTO.getAnimalType())
                .personality(villagerDTO.getPersonality())
                .friendshipLevel(villagerDTO.getFriendshipLevel())
                .happiness(villagerDTO.getHappiness())
                .hunger(villagerDTO.getHunger())
                .energy(villagerDTO.getEnergy())
                .healthLevel(villagerDTO.getHealthLevel())
                .stacks(villagerDTO.getStacks())
                .lastSleep(villagerDTO.getLastSleep())
                .build();
    }
}

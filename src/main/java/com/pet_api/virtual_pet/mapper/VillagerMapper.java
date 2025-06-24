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
                //.friendship(villager.getFriendship())
                .friendshipLevel(villager.getFriendshipLevel())
                .happiness(villager.getHappiness())
                .hunger(villager.getHunger())
                .energy(villager.getEnergy())
                //.health(villager.getHealth())
                .healthLevel(villager.getHealthLevel())
                //.accessory(villager.getAccessory())
                .stacks(villager.getStacks())
                .lastSleep(villager.getLastSleep())
                .build();
    }

    public Villager toEntity(VillagerDTO villagerDTO) {
        return Villager.builder()
                .villagerId(villagerDTO.getVillagerId())
                .villagerName(villagerDTO.getVillagerName())
                .animalType(villagerDTO.getAnimalType())
                .personality(villagerDTO.getPersonality())
                //.friendship(villagerDTO.getFriendship())
                .friendshipLevel(villagerDTO.getFriendshipLevel())
                .happiness(villagerDTO.getHappiness())
                .hunger(villagerDTO.getHunger())
                .energy(villagerDTO.getEnergy())
                //.health(villagerDTO.getHealth())
                .healthLevel(villagerDTO.getHealthLevel())
                //.accessory(villagerDTO.getAccessory())
                .stacks(villagerDTO.getStacks())
                .lastSleep(villagerDTO.getLastSleep())
                .build();
    }
}

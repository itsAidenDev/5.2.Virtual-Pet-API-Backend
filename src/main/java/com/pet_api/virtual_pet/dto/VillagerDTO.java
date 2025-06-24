package com.pet_api.virtual_pet.dto;


import com.pet_api.virtual_pet.utils.AnimalType;
import com.pet_api.virtual_pet.utils.Personality;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class VillagerDTO {
    private Long villagerId;
    private String villagerName;

    private AnimalType animalType;
    private Personality personality;

    //private int friendship;
    private int friendshipLevel;
    private int happiness;
    private int hunger;
    private int energy;
    //private int health;
    private int healthLevel;

    private String accessory;
    private List<String> stacks;
    private LocalDateTime lastSleep;
}

package com.pet_api.virtual_pet.mapper;

import com.pet_api.virtual_pet.dto.activities.FishDTO;
import com.pet_api.virtual_pet.model.activities.Fish;
import org.springframework.stereotype.Component;

@Component
public class FishMapper {

    public FishDTO toDto(Fish fish) {
        return FishDTO.builder()
                .fishId(fish.getFishId())
                .fishName(fish.getFishName())
                .fishDescription(fish.getFishDescription())
                .fishRarity(fish.getFishRarity())
                .fishValue(fish.getFishValue())
                .habitat(fish.getHabitat())
                .catchDifficulty(fish.getCatchDifficulty())
                .build();
    }

    public Fish toEntity(FishDTO fishDTO) {
        return Fish.builder()
                .fishId(fishDTO.getFishId())
                .fishName(fishDTO.getFishName())
                .fishDescription(fishDTO.getFishDescription())
                .fishRarity(fishDTO.getFishRarity())
                .fishValue(fishDTO.getFishValue())
                .habitat(fishDTO.getHabitat())
                .catchDifficulty(fishDTO.getCatchDifficulty())
                .build();
    }
}
package com.pet_api.virtual_pet.mapper;

import com.pet_api.virtual_pet.dto.shop.FurnitureDTO;
import com.pet_api.virtual_pet.model.decoration.Furniture;
import org.springframework.stereotype.Component;

@Component
public class FurnitureMapper {

    public FurnitureDTO toDto(Furniture furniture) {
        return FurnitureDTO.builder()
                .id(furniture.getId())
                .name(furniture.getName())
                .description(furniture.getDescription())
                .price(furniture.getPrice())
                .category(furniture.getCategory())
                .imageUrl(furniture.getImageUrl())
                .happinessBoost(furniture.getHappinessBoost())
                .energyBoost(furniture.getEnergyBoost())
                .rarity(furniture.getRarity())
                .isInteractive(furniture.isInteractive())
                .placed(false)
                .positionX(null)
                .positionY(null)
                .build();
    }

    public Furniture toEntity(FurnitureDTO furnitureDTO) {
        return Furniture.builder()
                .id(furnitureDTO.getId())
                .name(furnitureDTO.getName())
                .description(furnitureDTO.getDescription())
                .price(furnitureDTO.getPrice())
                .category(furnitureDTO.getCategory())
                .imageUrl(furnitureDTO.getImageUrl())
                .happinessBoost(furnitureDTO.getHappinessBoost())
                .energyBoost(furnitureDTO.getEnergyBoost())
                .rarity(furnitureDTO.getRarity())
                .isInteractive(furnitureDTO.isInteractive())
                .build();
    }
}

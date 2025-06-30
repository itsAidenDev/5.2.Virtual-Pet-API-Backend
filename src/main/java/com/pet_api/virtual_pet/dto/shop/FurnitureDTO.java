package com.pet_api.virtual_pet.dto.shop;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FurnitureDTO {
    private Long id;
    private String name;
    private String description;
    private int price;
    private String category;
    private String imageUrl;
    private int happinessBoost;
    private int energyBoost;
    private String rarity;
    private boolean isInteractive;
    private boolean placed;
    private Integer positionX;
    private Integer positionY;
}
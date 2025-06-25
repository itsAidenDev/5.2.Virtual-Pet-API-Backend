package com.pet_api.virtual_pet.dto.activities;

import com.pet_api.virtual_pet.utils.Habitat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FishDTO {
    private Long fishId;
    private String fishName;
    private String fishDescription;
    private String fishRarity;
    private int fishValue;
    private Habitat habitat;
    private double catchDifficulty;
    private LocalDateTime caughtAt; // Only for caught fish
    private String location; // Only for caught fish
}
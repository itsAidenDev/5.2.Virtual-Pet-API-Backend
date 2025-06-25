package com.pet_api.virtual_pet.dto.activities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResultDTO {
    private boolean success;
    private String message;
    private Object caughtItem; // Can be BugDTO or FishDTO
    private int experienceGained;
    private int friendshipGained;
}
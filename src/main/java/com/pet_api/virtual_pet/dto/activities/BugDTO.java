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
public class BugDTO {
    private Long bugId;
    private String bugName;
    private String bugDescription;
    private String bugRarity;
    private int bugValue;
    private Habitat bugHabitat;
    private double catchDifficulty;
    private LocalDateTime caughtAt; // Only for caught bugs
    private String location; // Only for caught bugs
}
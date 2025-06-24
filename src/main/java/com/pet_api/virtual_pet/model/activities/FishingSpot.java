package com.pet_api.virtual_pet.model.activities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FishingSpot {
    private int fishingSpotId;
    private String fishingSpotName;
    private String fishingSpotDescription;
    private String fishingSpotLocation;
    private List<Fish> availableFish;

    public FishingSpot(int fishingSpotId, String fishingSpotName, String fishingSpotDescription, String fishingSpotLocation, List<Fish> availableFish) {
        this.fishingSpotId = fishingSpotId;
        this.fishingSpotName = fishingSpotName;
        this.fishingSpotDescription = fishingSpotDescription;
        this.fishingSpotLocation = fishingSpotLocation;
        this.availableFish = availableFish;
    }
}

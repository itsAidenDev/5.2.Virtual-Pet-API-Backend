package com.pet_api.virtual_pet.utils;

import lombok.Getter;

@Getter
public enum VillagerStatus {
    HAPPY("Happy"),
    NEUTRAL("Neutral"),
    SAD("Sad"),
    SICK("Sick"),
    ASLEEP("Asleep");

    private final String villagerStatusName;

    VillagerStatus(String villagerStatusName) {
        this.villagerStatusName = villagerStatusName;
    }
}

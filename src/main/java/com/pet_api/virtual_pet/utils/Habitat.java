package com.pet_api.virtual_pet.utils;

import lombok.Getter;

@Getter
public enum Habitat {
    RIVER("River"),
    OCEAN("Ocean"),
    POND("Pond"),
    FOREST("Forest"),
    GRASSLAND("Grassland"),
    DESERT("Desert");

    private final String habitatName;

    Habitat(String habitatName) {
        this.habitatName = habitatName;
    }
}
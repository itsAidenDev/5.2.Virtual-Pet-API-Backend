package com.pet_api.virtual_pet.utils;

import lombok.Getter;

@Getter
public enum AnimalType {
    WOLF("Wolf"),
    CAT("Cat"),
    DOG("Dog"),
    EAGLE("Eagle"),
    TIGER("Tiger"),
    MOUSE("Mouse");

    private final String animalTypeName;

    AnimalType(String animalTypeName) {
        this.animalTypeName = animalTypeName;
    }
}

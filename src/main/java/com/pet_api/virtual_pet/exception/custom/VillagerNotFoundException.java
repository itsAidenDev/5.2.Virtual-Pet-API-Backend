package com.pet_api.virtual_pet.exception.custom;

public class VillagerNotFoundException extends RuntimeException {
    public VillagerNotFoundException(String message) {
        super(message);
    }
}

package com.pet_api.virtual_pet.service;

import com.pet_api.virtual_pet.model.Villager;

public interface VillagerActionHandler {
    Villager execute(Long petId, String parameter);
}

package com.pet_api.virtual_pet.service;


import com.pet_api.virtual_pet.exception.custom.HandleGenericException;
import com.pet_api.virtual_pet.model.Villager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class VillagerActionService {

    private final Map<String, VillagerActionHandler> handlers;

    public Villager performAction(String action, Long villagerId, String parameter) {
        VillagerActionHandler handler = handlers.get(action.toLowerCase());
        if (handler == null) {
            throw new HandleGenericException("Unknown action: " + action);
        }
        return handler.execute(villagerId, parameter);
    }
}

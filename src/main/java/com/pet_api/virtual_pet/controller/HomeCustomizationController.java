/*package com.pet_api.virtual_pet.controller;

import com.pet_api.virtual_pet.model.decoration.Furniture;
import com.pet_api.virtual_pet.model.decoration.Home;
import com.pet_api.virtual_pet.service.decoration.HomeCustomizationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeCustomizationController {
    private HomeCustomizationService service;

    public HomeCustomizationController(HomeCustomizationService service) {
        this.service = service;
    }

    @GetMapping("/homes")
    public List<Home> getHomes() {
        return service.getHomes();
    }

    @GetMapping("/furniture")
    public List<Furniture> getFurniture() {
        return service.getFurniture();
    }

    @PostMapping("/homes/{homeId}/furniture")
    public void addFurnitureToHome(@PathVariable Long homeId, @RequestBody Furniture furniture) {
        service.addFurnitureToHome(homeId, furniture);
    }

    @DeleteMapping("/homes/{homeId}/furniture/{furnitureId}")
    public void removeFurnitureFromHome(@PathVariable Long homeId, @PathVariable Long furnitureId) {
        service.removeFurnitureFromHome(homeId, furnitureId);
    }
}
*/
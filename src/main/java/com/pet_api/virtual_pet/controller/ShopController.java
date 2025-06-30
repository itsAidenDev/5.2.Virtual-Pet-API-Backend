package com.pet_api.virtual_pet.controller;

import com.pet_api.virtual_pet.dto.shop.FurnitureDTO;
import com.pet_api.virtual_pet.model.decoration.Furniture;
import com.pet_api.virtual_pet.repository.FurnitureRepository;
import com.pet_api.virtual_pet.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ShopController {

    private final ShopService shopService;

    @Autowired
    private FurnitureRepository furnitureRepository;

    @GetMapping("/furniture")
    public ResponseEntity<List<FurnitureDTO>> getAllFurniture() {
        List<Furniture> furniture = furnitureRepository.findAll();
        return ResponseEntity.ok(shopService.getAllFurniture());
    }

    @GetMapping("/furniture/category/{category}")
    public ResponseEntity<List<FurnitureDTO>> getFurnitureByCategory(@PathVariable String category) {
        return ResponseEntity.ok(shopService.getFurnitureByCategory(category));
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseFurniture(
            @RequestBody Map<String, Long> request,
            Authentication authentication) {
        Long furnitureId = request.get("furnitureId");
        Long villagerId = request.get("villagerId");

        shopService.purchaseFurniture(authentication.getName(), furnitureId, villagerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/place")
    public ResponseEntity<?> placeFurniture(
            @RequestBody Map<String, Object> request,
            Authentication authentication) {
        Long furnitureId = Long.parseLong(request.get("furnitureId").toString());
        int x = (int) request.get("x");
        int y = (int) request.get("y");

        shopService.placeFurniture(authentication.getName(), furnitureId, x, y);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/interact/{furnitureId}")
    public ResponseEntity<?> interactWithFurniture(
            @PathVariable Long furnitureId,
            Authentication authentication) {
        shopService.interactWithFurniture(authentication.getName(), furnitureId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/villager/{villagerId}/furniture")
    public ResponseEntity<List<FurnitureDTO>> getVillagerFurniture(
            @PathVariable Long villagerId,
            Authentication authentication) {
        return ResponseEntity.ok(shopService.getVillagerFurniture(authentication.getName(), villagerId));
    }
}


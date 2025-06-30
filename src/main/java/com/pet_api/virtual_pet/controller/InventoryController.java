package com.pet_api.virtual_pet.controller;
import com.pet_api.virtual_pet.dto.inventory.InventoryResponseDTO;
import com.pet_api.virtual_pet.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<InventoryResponseDTO> getUserInventory(Authentication authentication) {
        try {
            String username = authentication.getName();
            return ResponseEntity.ok(inventoryService.getUserInventory(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getInventoryStats(Authentication authentication) {
        try {
            String username = authentication.getName();
            return ResponseEntity.ok(inventoryService.getInventoryStats(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<String> sellInventoryItem(
            @PathVariable String itemId,
            Authentication authentication) {
        try {
            String username = authentication.getName();
            return ResponseEntity.ok(inventoryService.sellInventoryItem(username, itemId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not sell item: " + e.getMessage());
        }
    }

    @PostMapping("/item/use/{itemId}")
    public ResponseEntity<String> useInventoryItem(
            @PathVariable String itemId,
            Authentication authentication) {
        try {
            String username = authentication.getName();
            return ResponseEntity.ok(inventoryService.useInventoryItem(username, itemId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not use item: " + e.getMessage());
        }
    }
}
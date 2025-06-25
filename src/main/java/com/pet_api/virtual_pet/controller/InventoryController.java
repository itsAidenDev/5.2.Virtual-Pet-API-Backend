package com.pet_api.virtual_pet.controller;

import com.pet_api.virtual_pet.dto.inventory.InventoryResponseDTO;
import com.pet_api.virtual_pet.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:3000")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<InventoryResponseDTO> getUserInventory(Authentication authentication) {
        try {
            String username = authentication.getName();
            InventoryResponseDTO inventory = inventoryService.getUserInventory(username);
            return ResponseEntity.ok(inventory);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getInventoryStats(Authentication authentication) {
        try {
            String username = authentication.getName();
            var stats = inventoryService.getInventoryStats(username);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<String> deleteInventoryItem(
            @PathVariable String itemId,
            Authentication authentication) {
        try {
            String username = authentication.getName();
            inventoryService.deleteInventoryItem(username, itemId);
            return ResponseEntity.ok("Item deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not delete item: " + e.getMessage());
        }
    }

    @PostMapping("/item/{itemId}/use")
    public ResponseEntity<String> useInventoryItem(
            @PathVariable String itemId,
            Authentication authentication) {
        try {
            String username = authentication.getName();
            String result = inventoryService.useInventoryItem(username, itemId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not use item: " + e.getMessage());
        }
    }
}

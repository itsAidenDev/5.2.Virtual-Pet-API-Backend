package com.pet_api.virtual_pet.controller;

import com.pet_api.virtual_pet.dto.ActionResultDTO;
import com.pet_api.virtual_pet.dto.TalkResponseDTO;
import com.pet_api.virtual_pet.dto.VillagerDTO;
import com.pet_api.virtual_pet.mapper.VillagerMapper;
import com.pet_api.virtual_pet.model.User;
import com.pet_api.virtual_pet.security.AuthUtil;
import com.pet_api.virtual_pet.service.VillagerActionService;
import com.pet_api.virtual_pet.service.VillagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/villagers")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class VillagerController {

    private final VillagerService villagerService;
    private final AuthUtil authUtil;
    private final VillagerMapper villagerMapper;
    private final VillagerActionService villagerActionService;

    @GetMapping
    public ResponseEntity<List<VillagerDTO>> getAllVillagers() {
        return ResponseEntity.ok(villagerService.getAllVillagers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VillagerDTO> getVillagerById(@PathVariable Long id) {
        return ResponseEntity.ok(villagerService.getVillagerById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<VillagerDTO> createVillager(@RequestBody VillagerDTO villagerDTO) {
        User currentUser = authUtil.getCurrentUser();
        VillagerDTO createdVillager = villagerService.createVillagerForUser(villagerDTO, currentUser);
        return ResponseEntity.ok(createdVillager);
    }

    @PutMapping("/{villagerId}")
    public ResponseEntity<VillagerDTO> updateVillager(@PathVariable Long villagerId, @RequestBody VillagerDTO villagerDTO) {
        return ResponseEntity.ok(villagerService.updateVillager(villagerId, villagerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVillager(@PathVariable Long id, Authentication authentication) {
        try {
            String result = villagerService.deleteVillager(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not delete villager: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<VillagerDTO> updateVillagerName(
            @PathVariable Long id,
            @RequestBody Map<String, String> nameUpdate) {
        String newName = nameUpdate.get("name");
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        return ResponseEntity.ok(villagerService.updateVillagerName(id, newName));
    }

    @PostMapping("/{id}/sleep")
    public ResponseEntity<ActionResultDTO> sleep(@PathVariable Long id) {
        return ResponseEntity.ok(villagerService.sleep(id));
    }

    @PostMapping("/{id}/talk")
    public ResponseEntity<TalkResponseDTO> talk(@PathVariable Long id) {
        return ResponseEntity.ok(villagerService.talk(id));
    }

    @PostMapping("/{id}/give-gift")
    public ResponseEntity<ActionResultDTO> giveGift(@PathVariable Long id) {
        return ResponseEntity.ok(villagerService.giveGift(id));
    }

    @PostMapping("/{id}/play")
    public ResponseEntity<ActionResultDTO> play(@PathVariable Long id) {
        return ResponseEntity.ok(villagerService.play(id));
    }

    @PostMapping("/{id}/feed")
    public ResponseEntity<ActionResultDTO> feed(@PathVariable Long id) {
        return ResponseEntity.ok(villagerService.feed(id));
    }

    @PostMapping("/{id}/heal")
    public ResponseEntity<ActionResultDTO> heal(@PathVariable Long id) {
        return ResponseEntity.ok(villagerService.heal(id));
    }
}
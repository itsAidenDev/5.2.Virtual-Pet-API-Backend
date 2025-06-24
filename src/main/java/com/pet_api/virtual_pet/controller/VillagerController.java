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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/villagers")
@RequiredArgsConstructor
public class VillagerController {

    private final VillagerService villagerService;
    private final AuthUtil authUtil;
    private final VillagerMapper villagerMapper;
    private final VillagerActionService villagerActionService;

    @GetMapping("/getAllVillagers")
    public ResponseEntity<List<VillagerDTO>> getAllVillagers() {
        return ResponseEntity.ok(villagerService.getAllVillagers());
    }

    @PostMapping("/createVillager")
    public ResponseEntity<VillagerDTO> createVillager(@RequestBody VillagerDTO villagerDTO) {
        User currentUser = authUtil.getCurrentUser();
        VillagerDTO createdVillager = villagerService.createVillagerForUser(villagerDTO, currentUser);
            return ResponseEntity.ok(createdVillager);
    }

    @PutMapping("/updateVillager/{villagerId}")
    public ResponseEntity<VillagerDTO> updateVillager(@PathVariable Long villagerId, @RequestBody VillagerDTO villagerDTO) {
        return ResponseEntity.ok(villagerService.updateVillager(villagerId, villagerDTO));
    }

    @DeleteMapping("/deleteVillager/{villagerId}")
    public ResponseEntity<Void> deleteVillager(@PathVariable Long id) {
        villagerService.deleteVillager(id);
        return ResponseEntity.noContent().build();
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
}
    /*
    @PostMapping("/{id}/action")
    public ResponseEntity<VillagerDTO> performAction(
            @PathVariable Long id,
            @RequestBody @Valid VillagerActionRequest request
    ) {
        Villager villager = villagerActionService.performAction(request.getAction(), id, request.getParameter());
        VillagerDTO updated = villagerMapper.toDto(villager);
        return ResponseEntity.ok(updated);
    }
    */


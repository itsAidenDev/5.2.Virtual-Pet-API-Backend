package com.pet_api.virtual_pet.controller;

import com.pet_api.virtual_pet.dto.activities.BugDTO;
import com.pet_api.virtual_pet.dto.activities.FishDTO;
import com.pet_api.virtual_pet.service.MuseumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/museum")
@RequiredArgsConstructor
public class MuseumController {

    private final MuseumService museumService;

    @GetMapping("/villagers/{villagerId}/bugs")
    public ResponseEntity<List<BugDTO>> getMuseumBugs(@PathVariable Long villagerId) {
        return ResponseEntity.ok(museumService.getMuseumBugs(villagerId));
    }

    @GetMapping("/villagers/{villagerId}/fish")
    public ResponseEntity<List<FishDTO>> getMuseumFish(@PathVariable Long villagerId) {
        return ResponseEntity.ok(museumService.getMuseumFish(villagerId));
    }
}
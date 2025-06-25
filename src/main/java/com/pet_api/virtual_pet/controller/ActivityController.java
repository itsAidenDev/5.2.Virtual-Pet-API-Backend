package com.pet_api.virtual_pet.controller;

import com.pet_api.virtual_pet.dto.activities.ActivityResultDTO;
import com.pet_api.virtual_pet.dto.activities.BugDTO;
import com.pet_api.virtual_pet.dto.activities.FishDTO;
import com.pet_api.virtual_pet.service.activities.BugCatchingService;
import com.pet_api.virtual_pet.service.activities.FishingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final BugCatchingService bugCatchingService;
    private final FishingService fishingService;

    @GetMapping("/bugs")
    public ResponseEntity<List<BugDTO>> getAllBugs() {
        return ResponseEntity.ok(bugCatchingService.getAllBugs());
    }

    @GetMapping("/fish")
    public ResponseEntity<List<FishDTO>> getAllFish() {
        return ResponseEntity.ok(fishingService.getAllFish());
    }

    @PostMapping("/villagers/{villagerId}/catch-bug")
    public ResponseEntity<ActivityResultDTO> catchBug(@PathVariable Long villagerId, @RequestParam String habitat) {
        return ResponseEntity.ok(bugCatchingService.attemptBugCatch(villagerId, habitat));
    }

    @PostMapping("/villagers/{villagerId}/catch-fish")
    public ResponseEntity<ActivityResultDTO> catchFish(@PathVariable Long villagerId, @RequestParam String habitat) {
        return ResponseEntity.ok(fishingService.attemptFishCatch(villagerId, habitat));
    }

    @GetMapping("/villagers/{villagerId}/caught-bugs")
    public ResponseEntity<List<BugDTO>> getCaughtBugs(@PathVariable Long villagerId) {
        return ResponseEntity.ok(bugCatchingService.getCaughtBugs(villagerId));
    }

    @GetMapping("/villagers/{villagerId}/caught-fish")
    public ResponseEntity<List<FishDTO>> getCaughtFish(@PathVariable Long villagerId) {
        return ResponseEntity.ok(fishingService.getCaughtFish(villagerId));
    }
}
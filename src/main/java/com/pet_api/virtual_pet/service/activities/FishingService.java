package com.pet_api.virtual_pet.service.activities;

import com.pet_api.virtual_pet.model.activities.Fish;
import com.pet_api.virtual_pet.model.activities.FishingSpot;
import com.pet_api.virtual_pet.utils.Habitat;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FishingService {
    @Getter
    private List<FishingSpot> fishingSpots;
    private List<Fish> fish;
    private Random random = new Random();

    public FishingService() {
        this.fishingSpots = new ArrayList<>();
        this.fish = new ArrayList<>();

        Fish goldfish = new Fish(1, "Goldfish", "A common fish", "common", "goldfish.png", Habitat.POND);
        Fish cod = new Fish(2, "Cod", "A small fish", "common", "cod.png", Habitat.POND);
        Fish sardine = new Fish(3, "Sardine", "A small fish", "common", "sardine.png", Habitat.OCEAN);
        Fish tuna = new Fish(4, "Tuna", "A large fish", "uncommon", "tuna.png", Habitat.OCEAN);
        Fish salmon = new Fish(5, "Salmon", "A popular fish", "uncommon", "salmon.png", Habitat.RIVER);
        Fish trout = new Fish(6, "Trout", "A small fish", "rare", "trout.png", Habitat.RIVER);
        Fish shark = new Fish(7, "Shark", "A dangerous fish", "legendary", "shark.png", Habitat.OCEAN);

        fish.add(goldfish);
        fish.add(cod);
        fish.add(sardine);
        fish.add(tuna);
        fish.add(salmon);
        fish.add(trout);
        fish.add(shark);

        FishingSpot pond = new FishingSpot(1, "Pond", "A peaceful pond", "park", Arrays.asList(goldfish, cod));
        FishingSpot river = new FishingSpot(2, "River", "A fast-moving river", "mountains", Arrays.asList(salmon, trout));
        FishingSpot ocean = new FishingSpot(3, "Ocean", "A vast ocean", "ocean", Arrays.asList(sardine, tuna, shark));

        fishingSpots.add(pond);
        fishingSpots.add(river);
        fishingSpots.add(ocean);
    }

    public Fish catchFish(FishingSpot spot) {
        // check if the spot is valid
        if (!fishingSpots.contains(spot)) {
            return null;
        }

        // get the list of available fish at the spot
        List<Fish> availableFish = spot.getAvailableFish();

        // if there are no fish available, return null
        if (availableFish.isEmpty()) {
            return null;
        }

        // calculate the chance of catching a fish based on the spot's difficulty
        double chance = 0.5; // default chance
        switch (spot.getFishingSpotName()) {
            case "Pond":
                chance = 0.7; // easier to catch fish in a pond
                break;
            case "River":
                chance = 0.3; // harder to catch fish in a river
                break;
        }

        // roll a random number to determine if the player catches a fish
        double roll = random.nextDouble();
        if (roll < chance) {
            // player catches a fish, return a random fish from the available list
            return availableFish.get(random.nextInt(availableFish.size()));
        } else {
            // player doesn't catch a fish, return null
            return null;
        }
    }
}


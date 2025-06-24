package com.pet_api.virtual_pet.service.activities;

import com.pet_api.virtual_pet.model.activities.Bug;
import com.pet_api.virtual_pet.model.activities.BugCatchingSpot;
import com.pet_api.virtual_pet.utils.Habitat;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class BugCatchingService {
    @Getter
    private List<BugCatchingSpot> bugCatchingSpots;
    private List<Bug> bugs;
    private Random random = new Random();

    public BugCatchingService() {
        this.bugCatchingSpots = new ArrayList<>();
        this.bugs = new ArrayList<>();

        Bug bee = new Bug(1, "Bee", "A busy bug", "common", "bee.png", Habitat.GRASSLAND);
        Bug ant = new Bug(2, "Ant", "A tiny bug", "common", "ant.png", Habitat.FOREST);
        Bug butterfly = new Bug(3, "Butterfly", "A beautiful bug", "uncommon", "butterfly.png", Habitat.FOREST);
        Bug spider = new Bug(4, "Spider", "An intimidating bug", "rare", "spider.png", Habitat.FOREST);
        Bug scorpion = new Bug(5,"Scorpion", "A dangerous bug", "rare", "scorpion.png", Habitat.DESERT);

        bugs.add(bee);
        bugs.add(ant);
        bugs.add(butterfly);
        bugs.add(spider);
        bugs.add(scorpion);

        BugCatchingSpot forest = new BugCatchingSpot(1, "Forest", "A dense forest", "woods", Arrays.asList(butterfly, ant, spider));
        BugCatchingSpot grassland = new BugCatchingSpot(2, "Grassland", "A open grassland", "plains", Arrays.asList(bee));
        BugCatchingSpot desert = new BugCatchingSpot(3, "Desert", "A vast desert", "desert", Arrays.asList(scorpion));

        bugCatchingSpots.add(forest);
        bugCatchingSpots.add(grassland);
        bugCatchingSpots.add(desert);
    }

    public Bug catchBug(BugCatchingSpot spot) {
        // check if the spot is valid
        if (!bugCatchingSpots.contains(spot)) {
            return null;
        }

        // get the list of available bugs at the spot
        List<Bug> availableBugs = spot.getAvailableBugs();

        // if there are no bugs available, return null
        if (availableBugs.isEmpty()) {
            return null;
        }

        // calculate the chance of catching a bug based on the spot's difficulty
        double chance = 0.5; // default chance
        switch (spot.getBugCatchingSpotName()) {
            case "Forest":
                chance = 0.4; // harder to catch bugs in a forest
                break;
            case "Grassland":
                chance = 0.6; // easier to catch bugs in a grassland
                break;
        }

        // roll a random number to determine if the player catches a bug
        double roll = random.nextDouble();
        if (roll < chance) {
            // player catches a bug, return a random bug from the available list
            return availableBugs.get(random.nextInt(availableBugs.size()));
        } else {
            // player doesn't catch a bug, return null
            return null;
        }
    }
}
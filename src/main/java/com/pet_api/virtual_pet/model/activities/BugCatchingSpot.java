package com.pet_api.virtual_pet.model.activities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Table(name = "bug_catching_spots")
public class BugCatchingSpot {

    @Id
    @Column(name = "bug_catching_spot_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bugCatchingSpotId;

    private String bugCatchingSpotName;

    private String bugCatchingSpotDescription;

    private String bugCatchingSpotLocation;

    private List<Bug> availableBugs;

    public BugCatchingSpot(int bugCatchingSpotId, String bugCatchingSpotName, String bugCatchingSpotDescription, String bugCatchingSpotLocation, List<Bug> availableBugs) {
        this.bugCatchingSpotId = bugCatchingSpotId;
        this.bugCatchingSpotName = bugCatchingSpotName;
        this.bugCatchingSpotDescription = bugCatchingSpotDescription;
        this.bugCatchingSpotLocation = bugCatchingSpotLocation;
        this.availableBugs = availableBugs;
    }
}
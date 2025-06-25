package com.pet_api.virtual_pet.model;

import com.pet_api.virtual_pet.model.activities.CaughtBug;
import com.pet_api.virtual_pet.model.activities.CaughtFish;
import com.pet_api.virtual_pet.utils.AnimalType;
import com.pet_api.virtual_pet.utils.Personality;
import com.pet_api.virtual_pet.utils.VillagerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "villagers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Villager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long villagerId;

    @Column(name = "villager_name", nullable = false)
    private String villagerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_type", nullable = false)
    private AnimalType animalType;

    @Enumerated(EnumType.STRING)
    @Column(name = "villager_personality", nullable = false)
    private Personality personality;

    @Enumerated(EnumType.STRING)
    @Column(name = "villager_status", nullable = false)
    @Builder.Default
    private VillagerStatus villagerStatus = VillagerStatus.HAPPY;

    @Column(name = "villager_last_updated")
    @Builder.Default
    private LocalDateTime villagerLastUpdated = LocalDateTime.now();

    @Column(name = "friendship_level", nullable = false)
    @Builder.Default
    private int friendshipLevel = 0; // 0 - 100

    @Column(name = "happiness", nullable = false)
    @Builder.Default
    private int happiness = 50; // 0 - 100

    @Column(name = "hunger", nullable = false)
    @Builder.Default
    private int hunger = 50; // 0 - 100

    @Column(name = "energy", nullable = false)
    @Builder.Default
    private int energy = 100; // 0 - 100

    @Column(name = "health_level", nullable = false)
    @Builder.Default
    private int healthLevel = 100; // 0 - 100

    @Column(name = "last_sleep")
    @Builder.Default
    private LocalDateTime lastSleep = LocalDateTime.now();

    @ElementCollection
    @CollectionTable(
            name = "villager_stacks",
            joinColumns = @JoinColumn(name = "villager_id")
    )
    @Builder.Default
    private List<String> stacks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "villager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CaughtBug> caughtBugs = new ArrayList<>();

    @OneToMany(mappedBy = "villager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CaughtFish> caughtFish = new ArrayList<>();

    // Helper methods for stat management
    public void increaseHappiness(int amount) {
        this.happiness = Math.min(100, this.happiness + amount);
    }

    public void decreaseHappiness(int amount) {
        this.happiness = Math.max(0, this.happiness - amount);
    }

    public void increaseFriendship(int amount) {
        this.friendshipLevel = Math.min(100, this.friendshipLevel + amount);
    }

    public void decreaseFriendship(int amount) {
        this.friendshipLevel = Math.max(0, this.friendshipLevel - amount);
    }

    public void increaseEnergy(int amount) {
        this.energy = Math.min(100, this.energy + amount);
    }

    public void decreaseEnergy(int amount) {
        this.energy = Math.max(0, this.energy - amount);
    }

    public void increaseHealth(int amount) {
        this.healthLevel = Math.min(100, this.healthLevel + amount);
    }

    public void decreaseHealth(int amount) {
        this.healthLevel = Math.max(0, this.healthLevel - amount);
    }

    public void feed(int amount) {
        this.hunger = Math.max(0, this.hunger - amount);
        increaseHappiness(5);
        increaseHealth(2);
    }
}

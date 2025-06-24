package com.pet_api.virtual_pet.model;


import com.pet_api.virtual_pet.utils.AnimalType;
import com.pet_api.virtual_pet.utils.Personality;
import com.pet_api.virtual_pet.utils.VillagerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    private long villagerId;

    @Column(name = "villager_name", nullable = false)
    private String villagerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_type", nullable = false)
    private AnimalType animalType;

    @Enumerated(EnumType.STRING)
    @Column(name = "villager_personality", nullable = false)
    private Personality personality;

    @Column(name = "villager_birthday", nullable = false)
    private LocalDateTime villagerBirthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "villager_status", nullable = false)
    private VillagerStatus villagerStatus;

    @Column(name = "villager_last_updated")
    private LocalDateTime villagerLastUpdated;

    //private int friendship;

    @Column(name = "friendship_level", nullable = false)
    private int friendshipLevel; // 0 - 100

    @Column(name = "happiness", nullable = false)
    private int happiness; // 0 - 100

    @Column(name = "hunger", nullable = false)
    private int hunger; // 0 - 100

    @Column(name = "energy", nullable = false)
    private int energy; // 0 - 100
    //private int health;

    @Column(name = "health_level", nullable = false)
    private int healthLevel; // 0 - 100

    @Column(name = "last_sleep", nullable = false)
    private LocalDateTime lastSleep;

    //private List<Item> inventory;
    //private RoomDecoration roomDecoration;
    //private String accessory;         MAYBE DELETE THIS

    @ElementCollection
    @CollectionTable(
            name = "villager_stacks",
            joinColumns = @JoinColumn(name = "villager_id")
    )
    private List<String> stacks;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}

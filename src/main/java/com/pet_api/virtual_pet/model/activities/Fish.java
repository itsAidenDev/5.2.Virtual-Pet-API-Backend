package com.pet_api.virtual_pet.model.activities;

import com.pet_api.virtual_pet.utils.Habitat;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fish")
@Getter
@Setter
@NoArgsConstructor

public class Fish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fishIid;

    @Column(name = "fish_name", nullable = false)
    private String fishName;

    @Column(name = "fish_description", nullable = false)
    private String fishDescription;

    @Column(name = "fish_rarity", nullable = false)
    private String fishRarity;

    @Column(name = "fish_image", nullable = false)
    private String fishImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "fish_habitat", nullable = false)
    private Habitat habitat;

    public Fish(int fishIid, String fishName, String fishDescription, String fishRarity, String fishImage, Habitat habitat) {
        this.fishIid = fishIid;
        this.fishName = fishName;
        this.fishDescription = fishDescription;
        this.fishRarity = fishRarity;
        this.fishImage = fishImage;
        this.habitat = habitat;
    }
}


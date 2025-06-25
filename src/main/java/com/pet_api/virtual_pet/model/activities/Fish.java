package com.pet_api.virtual_pet.model.activities;

import com.pet_api.virtual_pet.utils.Habitat;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fish")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fishId;

    @Column(name = "fish_name", nullable = false)
    private String fishName;

    @Column(name = "fish_description", nullable = false)
    private String fishDescription;

    @Column(name = "fish_rarity", nullable = false)
    private String fishRarity;

    @Column(name = "fish_value", nullable = false)
    @Builder.Default
    private int fishValue = 100;

    @Enumerated(EnumType.STRING)
    @Column(name = "fish_habitat", nullable = false)
    private Habitat habitat;

    @Column(name = "catch_difficulty", nullable = false)
    @Builder.Default
    private double catchDifficulty = 0.5; // 0.0 = very easy, 1.0 = very hard
}

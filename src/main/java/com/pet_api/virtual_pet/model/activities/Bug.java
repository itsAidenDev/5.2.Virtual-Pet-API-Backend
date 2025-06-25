package com.pet_api.virtual_pet.model.activities;

import com.pet_api.virtual_pet.utils.Habitat;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bugs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bugId;

    @Column(name = "bug_name", nullable = false)
    private String bugName;

    @Column(name = "bug_description", nullable = false)
    private String bugDescription;

    @Column(name = "bug_rarity", nullable = false)
    private String bugRarity;

    @Column(name = "bug_value", nullable = false)
    @Builder.Default
    private int bugValue = 100;

    @Enumerated(EnumType.STRING)
    @Column(name = "bug_habitat", nullable = false)
    private Habitat bugHabitat;

    @Column(name = "catch_difficulty", nullable = false)
    @Builder.Default
    private double catchDifficulty = 0.5; // 0.0 = very easy, 1.0 = very hard
}

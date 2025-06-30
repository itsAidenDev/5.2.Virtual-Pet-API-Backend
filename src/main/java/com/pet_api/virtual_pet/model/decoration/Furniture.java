package com.pet_api.virtual_pet.model.decoration;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "furniture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Furniture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String category; // Ej: "bed", "chair", "table", "decoration"

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "happiness_boost")
    private int happinessBoost; // Aumento de felicidad al colocar el mueble

    @Column(name = "energy_boost")
    private int energyBoost; // Aumento de energía al interactuar con el mueble

    @Column(nullable = false)
    private String rarity; // "common", "uncommon", "rare", "legendary"

    @Column(name = "is_interactive", nullable = false)
    private boolean isInteractive; // Si el aldeano puede interactuar con este mueble
}
package com.pet_api.virtual_pet.model.activities;

import com.pet_api.virtual_pet.model.Villager;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "caught_fish")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaughtFish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "villager_id", nullable = false)
    private Villager villager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fish_id", nullable = false)
    private Fish fish;

    @Column(name = "caught_at", nullable = false)
    @Builder.Default
    private LocalDateTime caughtAt = LocalDateTime.now();

    @Column(name = "location")
    private String location;
}
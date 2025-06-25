package com.pet_api.virtual_pet.model.activities;

import com.pet_api.virtual_pet.model.Villager;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "caught_bugs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaughtBug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "villager_id", nullable = false)
    private Villager villager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bug_id", nullable = false)
    private Bug bug;

    @Column(name = "caught_at", nullable = false)
    @Builder.Default
    private LocalDateTime caughtAt = LocalDateTime.now();

    @Column(name = "location")
    private String location;
}
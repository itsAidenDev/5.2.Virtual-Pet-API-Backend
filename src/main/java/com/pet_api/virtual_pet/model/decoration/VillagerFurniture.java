package com.pet_api.virtual_pet.model.decoration;

import com.pet_api.virtual_pet.model.Villager;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "villager_furniture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VillagerFurniture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "villager_id", nullable = false)
    private Villager villager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "furniture_id", nullable = false)
    private Furniture furniture;

    @Column(name = "position_x")
    private Integer positionX;

    @Column(name = "position_y")
    private Integer positionY;

    @Column(name = "is_placed", nullable = false)
    private boolean isPlaced;

    @Column(name = "purchased_at", nullable = false)
    private LocalDateTime purchasedAt;

    @Column(name = "last_interacted")
    private LocalDateTime lastInteracted;
}

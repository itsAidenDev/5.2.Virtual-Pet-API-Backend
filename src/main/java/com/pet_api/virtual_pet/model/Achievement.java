package com.pet_api.virtual_pet.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "achievements")
@Getter
@Setter
@NoArgsConstructor
@Builder
class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long achievementId;

    @Column(nullable = false, unique = true)
    String achievementName;
    String achievementDescription;
    boolean achieved;
    int reward;

    public Achievement(Long achievementId, String achievementName, String achievementDescription, boolean achieved, int reward) {
        this.achievementId = achievementId;
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.achieved = achieved;
        this.reward = reward;
    }
}
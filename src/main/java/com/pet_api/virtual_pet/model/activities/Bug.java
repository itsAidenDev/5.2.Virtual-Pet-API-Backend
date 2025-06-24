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
    private int bugId;

    @Column(name = "bug_name", nullable = false)
    private String bugName;

    @Column(name = "bug_description", nullable = false)
    private String bugDescription;

    @Column(name = "bug_rarity", nullable = false)
    private String bugRarity;

    /*
    @Column(name = "bug_image", nullable = false)
    private String bugImage;
     */

    @Enumerated(EnumType.STRING)
    @Column(name = "bug_habitat", nullable = false)
    private Habitat bugHabitat;

    public Bug(int bugId, String bugName, String bugDescription, String bugRarity, String bugImage, Habitat BugHabitat) {
        this.bugId = bugId;
        this.bugName = bugName;
        this.bugDescription = bugDescription;
        this.bugRarity = bugRarity;
        // this.bugImage = bugImage;
        this.bugHabitat = BugHabitat;
    }
}

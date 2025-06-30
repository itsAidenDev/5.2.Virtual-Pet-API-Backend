package com.pet_api.virtual_pet.model.museum;

import com.pet_api.virtual_pet.model.Villager;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "museum_records")
public class MuseumRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "villager_id", nullable = false)
    private Long villagerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecordType recordType;

    @Column(nullable = false)
    private Long entityId;

    private LocalDateTime firstCaughtAt;
    private String location;

    public enum RecordType {
        BUG, FISH
    }
}
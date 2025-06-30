package com.pet_api.virtual_pet.repository.museum;

import com.pet_api.virtual_pet.model.Villager;
import com.pet_api.virtual_pet.model.museum.MuseumRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MuseumRecordRepository extends JpaRepository<MuseumRecord, Long> {
    boolean existsByVillagerIdAndRecordTypeAndEntityId(
            Long villagerId,
            MuseumRecord.RecordType recordType,
            Long entityId
    );

    List<MuseumRecord> findByVillagerIdAndRecordType(
            Long villagerId,
            MuseumRecord.RecordType recordType
    );
}
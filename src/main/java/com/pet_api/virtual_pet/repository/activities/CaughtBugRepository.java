package com.pet_api.virtual_pet.repository.activities;

import com.pet_api.virtual_pet.model.activities.CaughtBug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaughtBugRepository extends JpaRepository<CaughtBug, Long> {
    List<CaughtBug> findByVillagerVillagerId(Long villagerId);

    @Query("SELECT cb FROM CaughtBug cb WHERE cb.villager.villagerId = :villagerId AND cb.bug.id = :bugId ORDER BY cb.caughtAt ASC")
    List<CaughtBug> findFirstByVillagerIdAndBugIdOrderByCaughtAtAsc(
            @Param("villagerId") Long villagerId,
            @Param("bugId") Long bugId
    );

    @Query("SELECT DISTINCT cb.bug.id FROM CaughtBug cb WHERE cb.villager.villagerId = :villagerId")
    List<Long> findDistinctBugIdsByVillagerId(@Param("villagerId") Long villagerId);
}
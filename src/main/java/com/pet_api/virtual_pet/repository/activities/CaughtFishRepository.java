package com.pet_api.virtual_pet.repository.activities;

import com.pet_api.virtual_pet.model.activities.CaughtBug;
import com.pet_api.virtual_pet.model.activities.CaughtFish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaughtFishRepository extends JpaRepository<CaughtFish, Long> {
    List<CaughtFish> findByVillagerVillagerId(Long villagerId);

    @Query("SELECT cb FROM CaughtFish cb WHERE cb.villager.villagerId = :villagerId AND cb.fish.id = :fishId ORDER BY cb.caughtAt ASC")
    List<CaughtFish> findFirstByVillagerIdAndFishIdOrderByCaughtAtAsc(
            @Param("villagerId") Long villagerId,
            @Param("fishId") Long fishId
    );

    @Query("SELECT DISTINCT cb.fish.id FROM CaughtFish cb WHERE cb.villager.villagerId = :villagerId")
    List<Long> findDistinctFishIdsByVillagerId(@Param("villagerId") Long villagerId);
}

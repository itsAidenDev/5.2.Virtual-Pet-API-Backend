package com.pet_api.virtual_pet.repository;

import com.pet_api.virtual_pet.model.activities.CaughtBug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaughtBugRepository extends JpaRepository<CaughtBug, Long> {
    List<CaughtBug> findByVillagerVillagerId(Long villagerId);
}
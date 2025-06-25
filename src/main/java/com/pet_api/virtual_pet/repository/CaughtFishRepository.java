package com.pet_api.virtual_pet.repository;

import com.pet_api.virtual_pet.model.activities.CaughtFish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaughtFishRepository extends JpaRepository<CaughtFish, Long> {
    List<CaughtFish> findByVillagerVillagerId(Long villagerId);
}

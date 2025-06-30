package com.pet_api.virtual_pet.repository.activities;

import com.pet_api.virtual_pet.model.activities.Fish;
import com.pet_api.virtual_pet.utils.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FishRepository extends JpaRepository<Fish, Long> {
    List<Fish> findByHabitat(Habitat habitat);
    List<Fish> findByFishRarity(String rarity);
}
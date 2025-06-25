package com.pet_api.virtual_pet.repository;

import com.pet_api.virtual_pet.model.activities.Bug;
import com.pet_api.virtual_pet.utils.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {
    List<Bug> findByBugHabitat(Habitat habitat);
    List<Bug> findByBugRarity(String rarity);
}
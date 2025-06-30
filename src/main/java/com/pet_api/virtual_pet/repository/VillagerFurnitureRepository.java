package com.pet_api.virtual_pet.repository;

import com.pet_api.virtual_pet.model.Villager;
import com.pet_api.virtual_pet.model.decoration.VillagerFurniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VillagerFurnitureRepository extends JpaRepository<VillagerFurniture, Long> {
    List<VillagerFurniture> findByVillager(Villager villager);
}
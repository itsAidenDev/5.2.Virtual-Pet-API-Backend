package com.pet_api.virtual_pet.repository;

import com.pet_api.virtual_pet.model.decoration.Furniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FurnitureRepository extends JpaRepository<Furniture, Long> {
    List<Furniture> findByCategoryIgnoreCase(String category);
}

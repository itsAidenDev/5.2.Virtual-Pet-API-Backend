package com.pet_api.virtual_pet.repository;

import com.pet_api.virtual_pet.model.Villager;
import com.pet_api.virtual_pet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VillagerRepository extends JpaRepository<Villager, Long> {
    List<Villager> findByUser(User user);
}

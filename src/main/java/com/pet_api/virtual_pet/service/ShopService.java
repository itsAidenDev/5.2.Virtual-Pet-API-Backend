package com.pet_api.virtual_pet.service;

import com.pet_api.virtual_pet.dto.shop.FurnitureDTO;
import com.pet_api.virtual_pet.exception.custom.InsufficientFundsException;
import com.pet_api.virtual_pet.exception.custom.ResourceNotFoundException;
import com.pet_api.virtual_pet.exception.custom.UserNotFoundException;
import com.pet_api.virtual_pet.exception.custom.VillagerNotFoundException;
import com.pet_api.virtual_pet.mapper.FurnitureMapper;
import com.pet_api.virtual_pet.model.decoration.Furniture;
import com.pet_api.virtual_pet.model.User;
import com.pet_api.virtual_pet.model.Villager;
import com.pet_api.virtual_pet.model.decoration.VillagerFurniture;
import com.pet_api.virtual_pet.repository.FurnitureRepository;
import com.pet_api.virtual_pet.repository.UserRepository;
import com.pet_api.virtual_pet.repository.VillagerFurnitureRepository;
import com.pet_api.virtual_pet.repository.VillagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final FurnitureRepository furnitureRepository;
    private final UserRepository userRepository;
    private final VillagerRepository villagerRepository;
    private final VillagerFurnitureRepository villagerFurnitureRepository;
    private final FurnitureMapper furnitureMapper;

    public List<FurnitureDTO> getAllFurniture() {
        return furnitureRepository.findAll().stream()
                .map(furnitureMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<FurnitureDTO> getFurnitureByCategory(String category) {
        return furnitureRepository.findByCategoryIgnoreCase(category).stream()
                .map(furnitureMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void purchaseFurniture(String username, Long furnitureId, Long villagerId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Villager villager = villagerRepository.findById(villagerId)
                .orElseThrow(() -> new VillagerNotFoundException("Villager not found"));

        if (!villager.getUser().equals(user)) {
            throw new SecurityException("You don't own this villager");
        }

        Furniture furniture = furnitureRepository.findById(furnitureId)
                .orElseThrow(() -> new ResourceNotFoundException("Furniture not found"));

        if (user.getPoints() < furniture.getPrice()) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        user.setPoints(user.getPoints() - furniture.getPrice());
        userRepository.save(user);

        // Creates new furniture and adds it to the villager
        VillagerFurniture villagerFurniture = VillagerFurniture.builder()
                .villager(villager)
                .furniture(furniture)
                .purchasedAt(LocalDateTime.now())
                .isPlaced(false)
                .build();

        villagerFurnitureRepository.save(villagerFurniture);
    }

    @Transactional
    public void placeFurniture(String username, Long villagerFurnitureId, int x, int y) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        VillagerFurniture villagerFurniture = villagerFurnitureRepository.findById(villagerFurnitureId)
                .orElseThrow(() -> new VillagerNotFoundException("Furniture not found"));

        if (!villagerFurniture.getVillager().getUser().equals(user)) {
            throw new SecurityException("You don't own this furniture");
        }

        villagerFurniture.setPositionX(x);
        villagerFurniture.setPositionY(y);
        villagerFurniture.setPlaced(true);
        villagerFurnitureRepository.save(villagerFurniture);
    }

    @Transactional
    public void interactWithFurniture(String username, Long villagerFurnitureId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        VillagerFurniture villagerFurniture = villagerFurnitureRepository.findById(villagerFurnitureId)
                .orElseThrow(() -> new VillagerNotFoundException("Furniture not found"));

        if (!villagerFurniture.getVillager().getUser().equals(user)) {
            throw new SecurityException("You don't own this furniture");
        }

        if (!villagerFurniture.isPlaced() || !villagerFurniture.getFurniture().isInteractive()) {
            throw new IllegalStateException("This furniture cannot be interacted with");
        }

        Villager villager = villagerFurniture.getVillager();
        Furniture furniture = villagerFurniture.getFurniture();

        // Applies the effects of the furniture
        villager.setHappiness(Math.min(100, villager.getHappiness() + furniture.getHappinessBoost()));
        villager.setEnergy(Math.min(100, villager.getEnergy() + furniture.getEnergyBoost()));

        villagerFurniture.setLastInteracted(LocalDateTime.now());

        villagerRepository.save(villager);
        villagerFurnitureRepository.save(villagerFurniture);
    }

    public List<FurnitureDTO> getVillagerFurniture(String username, Long villagerId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Villager villager = villagerRepository.findById(villagerId)
                .orElseThrow(() -> new VillagerNotFoundException("Villager not found"));

        if (!villager.getUser().equals(user)) {
            throw new SecurityException("You don't own this villager");
        }

        return villagerFurnitureRepository.findByVillager(villager).stream()
                .map(vf -> {
                    FurnitureDTO dto = furnitureMapper.toDto(vf.getFurniture());
                    dto.setPlaced(vf.isPlaced());
                    dto.setPositionX(vf.getPositionX());
                    dto.setPositionY(vf.getPositionY());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
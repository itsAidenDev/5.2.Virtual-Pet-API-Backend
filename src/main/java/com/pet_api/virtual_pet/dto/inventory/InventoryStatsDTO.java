package com.pet_api.virtual_pet.dto.inventory;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventoryStatsDTO {
    private Integer totalItems;
    private Integer totalValue;
    private Integer uniqueSpecies;
    private Integer rareItems;

    public InventoryStatsDTO() {}

    public InventoryStatsDTO(Integer totalItems, Integer totalValue, Integer uniqueSpecies, Integer rareItems) {
        this.totalItems = totalItems;
        this.totalValue = totalValue;
        this.uniqueSpecies = uniqueSpecies;
        this.rareItems = rareItems;
    }
}

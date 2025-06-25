package com.pet_api.virtual_pet.dto.inventory;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InventoryResponseDTO {
    private List<InventoryItemDTO> items;
    private InventoryStatsDTO stats;

    public InventoryResponseDTO() {}

    public InventoryResponseDTO(List<InventoryItemDTO> items, InventoryStatsDTO stats) {
        this.items = items;
        this.stats = stats;
    }

}

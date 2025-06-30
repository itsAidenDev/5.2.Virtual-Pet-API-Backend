package com.pet_api.virtual_pet.dto.inventory;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class InventoryItemDTO {
    private String id;
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private String itemType; // "BUG" or "FISH"
    private String rarity;
    private Integer value;
    private String habitat;
    private LocalDateTime caughtAt;
    private String caughtBy;
    private String location;
    private Integer quantity;

    public InventoryItemDTO() {}

    public InventoryItemDTO(String id, Long itemId, String itemName, String itemDescription,
                            String itemType, String rarity, Integer value, String habitat,
                            LocalDateTime caughtAt, String caughtBy, String location, Integer quantity) {
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemType = itemType;
        this.rarity = rarity;
        this.value = value;
        this.habitat = habitat;
        this.caughtAt = caughtAt;
        this.caughtBy = caughtBy;
        this.location = location;
        this.quantity = quantity;
    }
}

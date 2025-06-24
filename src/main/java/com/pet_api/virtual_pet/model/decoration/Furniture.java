/*
package com.pet_api.virtual_pet.model.decoration;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "furniture")
@Getter
@Setter
public class Furniture {

    @Id
    @Column(name = "furniture_id")
    private int furnitureId;

    @Column(name = "furniture_name")
    private String furnitureName;

    @Column(name = "furniture_description")
    private String furnitureDescription;

    @Column(name = "furniture_price")
    private int furniturePrice;


    @Column(name = "furniture_image")
    private String furnitureImage;


    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;

    public Furniture(int furnitureId, String furnitureName, String furnitureDescription, int furniturePrice, String furnitureImage) {
        this.furnitureId = furnitureId;
        this.furnitureName = furnitureName;
        this.furnitureDescription = furnitureDescription;
        this.furniturePrice = furniturePrice;
        //this.furnitureImage = furnitureImage;
    }
}
*/
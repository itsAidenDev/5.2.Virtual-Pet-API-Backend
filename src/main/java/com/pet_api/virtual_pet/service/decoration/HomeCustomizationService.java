/*package com.pet_api.virtual_pet.service.decoration;

import com.pet_api.virtual_pet.model.decoration.Furniture;
import com.pet_api.virtual_pet.model.decoration.Home;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HomeCustomizationService {
    private List<Home> homes;
    private List<Furniture> furniture;

    public HomeCustomizationService() {
        this.homes = new ArrayList<>();
        this.furniture = new ArrayList<>();

        /*
        Furniture chair = new Furniture(1, "Chair", "A simple chair", 100, "chair.png");
        Furniture table = new Furniture(2, "Table", "A wooden table", 200, "table.png");
        Furniture bed = new Furniture(3, "Bed", "A cozy bed", 300, "bed.png");

        furniture.add(chair);
        furniture.add(table);
        furniture.add(bed);

        Home home = new Home(1, "My Home", "A cozy little home");
        homes.add(home);

    }

    public void addFurnitureToHome(Long homeId, Furniture furniture) {
        Home home = homes.stream().filter(h -> h.getHomeId() == homeId).findFirst().orElse(null);
        if (home != null) {
            home.addFurniture(furniture);
        }
    }

    public void removeFurnitureFromHome(Long homeId, Long furnitureId) {
        Home home = homes.stream().filter(h -> h.getHomeId() == homeId).findFirst().orElse(null);
        if (home != null) {
            Furniture furniture = home.getHomeFurniture().stream().filter(f -> f.getFurnitureId() == furnitureId.longValue()).findFirst().orElse(null);
            if (furniture != null) {
                home.removeFurniture(furniture);
            }
        }
    }
}
*/

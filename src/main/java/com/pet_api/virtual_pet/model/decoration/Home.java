/*package com.pet_api.virtual_pet.model.decoration;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "homes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Home {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long homeId;

    @OneToMany(mappedBy = "home")
    @Column(name = "furniture_id")
    private List<Furniture> homeFurniture;
    private String homeName;
    private String homeDescription;

    public Home(long homeId, String homeName, String homeDescription) {
        this.homeId = homeId;
        this.homeName = homeName;
        this.homeDescription = homeDescription;
        this.homeFurniture = new ArrayList<>();
    }

    public void addFurniture(Furniture furniture) {
        this.homeFurniture.add(furniture);
    }

    public void removeFurniture(Furniture furniture) {
        this.homeFurniture.remove(furniture);
    }

    public Collection<Object> getHomFurniture() {
        return Collections.singleton(this.homeFurniture);}
}
*/

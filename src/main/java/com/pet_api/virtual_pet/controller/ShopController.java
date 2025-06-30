package com.pet_api.virtual_pet.controller;

import com.pet_api.virtual_pet.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ShopController {

    @GetMapping("/shop")
    public List<Product> getShop() {
        List<Product> products = new ArrayList<>();

        products.add(new Product(1L, "Pez", 10.99));
        products.add(new Product(2L, "Mueble", 50.99));
        return products;
    }
}


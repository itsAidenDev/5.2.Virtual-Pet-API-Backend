package com.pet_api.virtual_pet.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}

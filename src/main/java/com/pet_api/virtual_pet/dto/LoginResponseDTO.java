package com.pet_api.virtual_pet.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private UserDTO user;
}

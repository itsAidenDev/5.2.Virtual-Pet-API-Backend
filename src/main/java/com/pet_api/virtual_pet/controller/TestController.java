package com.pet_api.virtual_pet.controller;

import com.pet_api.virtual_pet.security.JwtUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    private final JwtUtil jwtUtil;

    public TestController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/check-secret")
    public String getSecret() {
        return jwtUtil.generateToken("adminuser", "ROLE_ADMIN");
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userAccess() {
        return "Access allowed: ROLE_USER";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Access allowed: ROLE_ADMIN";
    }
}

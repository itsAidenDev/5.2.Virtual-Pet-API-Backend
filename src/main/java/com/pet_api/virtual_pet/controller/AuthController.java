package com.pet_api.virtual_pet.controller;

import com.pet_api.virtual_pet.dto.LoginRequest;
import com.pet_api.virtual_pet.dto.LoginResponseDTO;
import com.pet_api.virtual_pet.dto.UserDTO;
import com.pet_api.virtual_pet.model.User;
import com.pet_api.virtual_pet.repository.UserRepository;
import com.pet_api.virtual_pet.security.AuthUtil;
import com.pet_api.virtual_pet.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request){
        String username = request.getUsername();
        String password = request.getPassword();

        if(userRepository.findByUsername(username).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username already exists");
        }
        User newUser = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role("ROLE_USER")
                .build();
        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully " + username);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
                UserDTO userDTO = new UserDTO(user.getUsername(), user.getRole());
                return ResponseEntity.ok(new LoginResponseDTO(token, userDTO));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        User user = authUtil.getCurrentUser();
        UserDTO userDTO = new UserDTO(user.getUsername(), user.getRole());
        return ResponseEntity.ok(userDTO);
    }
}

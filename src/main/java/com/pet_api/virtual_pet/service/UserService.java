package com.pet_api.virtual_pet.service;

import com.pet_api.virtual_pet.model.User;
import com.pet_api.virtual_pet.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getUserId()).orElseThrow();
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(existingUser);
    }
}
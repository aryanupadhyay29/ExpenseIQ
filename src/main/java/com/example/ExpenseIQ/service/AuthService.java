package com.example.ExpenseIQ.service;

import com.example.ExpenseIQ.dto.LoginRequest;
import com.example.ExpenseIQ.model.User;
import com.example.ExpenseIQ.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private static UserRepository userRepository;

    @Autowired
    private static PasswordEncoder passwordEncoder;

    // Method to register a user, now using the autowired PasswordEncoder
    public static User registerUser(User request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }
    public User login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return user;
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
package com.example.ExpenseIQ.service;

import com.example.ExpenseIQ.dto.LoginRequest;
import com.example.ExpenseIQ.dto.RegisterRequest;
import com.example.ExpenseIQ.model.User;
import com.example.ExpenseIQ.repository.UserRepository;
import com.example.ExpenseIQ.utils.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    //  Register user
    public User register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(PasswordEncoderUtil.encode(request.getPassword())); // Hash password
        return userRepository.save(user);
    }

    //  Login user
    public String login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        User user = userOpt.get();

        if (!PasswordEncoderUtil.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        // In a real project, return JWT token here
        return "Login successful for user: " + user.getName();
    }
}

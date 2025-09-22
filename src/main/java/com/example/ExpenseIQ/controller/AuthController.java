package com.example.ExpenseIQ.controller;

import com.example.ExpenseIQ.Config.JwtUtil;
import com.example.ExpenseIQ.dto.LoginRequest;
import com.example.ExpenseIQ.model.User;
import com.example.ExpenseIQ.repository.UserRepository;
import com.example.ExpenseIQ.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserService userService,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String testAuth() {
        return "Auth endpoints are working!";
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);  // Use instance, not class
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.generateToken(loginRequest.getEmail());

        // 1. Create a Map to build the JSON response
        Map<String, String> response = new HashMap<>();

        // 2. Put the token into the map with a key (e.g., "token")
        response.put("token", token);

        // 3. Return the Map. Spring Boot will automatically convert it to JSON.
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new NoSuchElementException("User not found with email: " + email);
        }
        return ResponseEntity.ok(user);
    }
}

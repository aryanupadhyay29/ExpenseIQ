package com.example.ExpenseIQ.controller;

import com.example.ExpenseIQ.Config.JwtUtil;
import com.example.ExpenseIQ.dto.LoginRequest;
import com.example.ExpenseIQ.dto.LoginResponse;
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        String token = jwtUtil.generateToken(loginRequest.getEmail());


        User user = userService.getUserByEmail(loginRequest.getEmail());


        if (user == null) {

            throw new NoSuchElementException("Authenticated user data not found.");
        }

        LoginResponse response = new LoginResponse(token, user);

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

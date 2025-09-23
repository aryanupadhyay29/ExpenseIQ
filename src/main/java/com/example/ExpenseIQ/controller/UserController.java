package com.example.ExpenseIQ.controller;

import com.example.ExpenseIQ.model.User;
import com.example.ExpenseIQ.service.UserService;
import com.example.ExpenseIQ.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "User Service is up and running";
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return AuthService.registerUser(user);
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
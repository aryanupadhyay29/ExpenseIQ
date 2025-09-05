package com.example.ExpenseIQ.service;



import com.example.ExpenseIQ.model.User;
import com.example.ExpenseIQ.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    // Save/register a new User
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Find User by ID
    public  Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update User details
    public User updateUser(Long id , User user) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setName(user.getName());
                    existing.setEmail(user.getEmail());
                    existing.setPassword(user.getPassword());
                    return userRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Delete User by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveuser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}

package com.backend.Lakshya.controller;

import com.backend.Lakshya.model.User;
import com.backend.Lakshya.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // Import this
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository usersRepo;
    private final PasswordEncoder passwordEncoder; // Inject this

    // Constructor injection
    public UserController(UserRepository usersRepo, PasswordEncoder passwordEncoder) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return usersRepo.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepo.save(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return usersRepo.findById(id).orElseThrow();
    }
}
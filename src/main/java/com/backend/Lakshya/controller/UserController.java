package com.backend.Lakshya.controller;

import com.backend.Lakshya.model.User;
import com.backend.Lakshya.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository usersRepo;

    public UserController(UserRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return usersRepo.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return usersRepo.save(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return usersRepo.findById(id).orElseThrow();
    }
}

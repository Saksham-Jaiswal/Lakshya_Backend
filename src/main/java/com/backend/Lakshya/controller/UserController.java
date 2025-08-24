package com.backend.Lakshya.controller;

import com.backend.Lakshya.model.Users;
import com.backend.Lakshya.repository.UsersRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UsersRepository usersRepo;

    public UserController(UsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }

    @PostMapping
    public Users createUser(@RequestBody Users user) {
        return usersRepo.save(user);
    }

    @GetMapping("/{id}")
    public Users getUser(@PathVariable Long id) {
        return usersRepo.findById(id).orElseThrow();
    }
}

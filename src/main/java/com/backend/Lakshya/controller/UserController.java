package com.backend.Lakshya.controller;

import com.backend.Lakshya.dto.TransactionDTO;
import com.backend.Lakshya.dto.UserDTO;
import com.backend.Lakshya.model.User;
import com.backend.Lakshya.repository.UserRepository;
import com.backend.Lakshya.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder; // Import this
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository usersRepo;
    private final PasswordEncoder passwordEncoder; // Inject this
    private final UserService userService;

    // Constructor injection
    public UserController(UserRepository usersRepo, PasswordEncoder passwordEncoder, UserService userService) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
        this.userService=userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>>  getAllUsers() {
        List<UserDTO> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User newUser = userService.createUser(user);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            // Return 400 Bad Request with the message "User already exists..."
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return usersRepo.findById(id).orElseThrow();
    }
}
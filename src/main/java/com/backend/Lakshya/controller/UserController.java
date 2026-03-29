package com.backend.Lakshya.controller;

import com.backend.Lakshya.dto.TransactionDTO;
import com.backend.Lakshya.dto.UserDTO;
import com.backend.Lakshya.model.User;
import com.backend.Lakshya.repository.UserRepository;
import com.backend.Lakshya.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder; // Import this
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final PasswordEncoder passwordEncoder; // Inject this
    private final UserService userService;

    // Constructor injection
    public UserController(UserRepository usersRepo, PasswordEncoder passwordEncoder, UserService userService) {
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

//    @GetMapping("/{id}")
//    public User getUser(@PathVariable Long id) {
//        return usersRepo.findById(id).orElseThrow();
//    }
    // POST: /api/users/forgot-password
    @PostMapping("/forgot-password")
    public ResponseEntity<?> requestPasswordReset(@RequestBody Map<String, String> request) {
        try {
            userService.generatePasswordResetOTP(request.get("email"));
            return ResponseEntity.ok("OTP sent successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // POST: /api/users/reset-password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        try {
            userService.resetPasswordWithOTP(
                    request.get("email"),
                    request.get("otp"),
                    request.get("newPassword")
            );
            return ResponseEntity.ok("Password reset successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
package com.backend.Lakshya.service;

import com.backend.Lakshya.dto.UserDTO;
import com.backend.Lakshya.mapper.UserMapper;
import com.backend.Lakshya.model.Role;
import com.backend.Lakshya.model.User;
import com.backend.Lakshya.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service // 1. Tells Spring this is a Service bean
public class UserService {

    // 2. Define the dependencies as final fields
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // Store OTPs temporarily (In production, use Redis or a DB table with expirations)
    private Map<String, String> otpStorage = new HashMap<>();

    // 3. Create a constructor to inject these dependencies
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getUsers()
    {
        return userRepository.findAll().stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }
    public List<UserDTO> getSalespeopleByOwner(Long ownerId) {
        return userRepository.findByRoleAndOwnerId(Role.SALESPERSON, ownerId).stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    public User createUser(User user) {
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        // Hash password using the injected PasswordEncoder
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
    // Add this new method
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    public void generatePasswordResetOTP(String email) {
        // 1. Check if user exists
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email"));

        // 2. Generate a 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // 3. Save it temporarily
        otpStorage.put(email, otp);

        // 4. "Send" the email (For development, we just print it)
        System.out.println("=================================================");
        System.out.println("PASSWORD RESET OTP FOR " + email + " : " + otp);
        System.out.println("=================================================");

        // TODO: Later, integrate JavaMailSender to actually email this OTP
    }

    public void resetPasswordWithOTP(String email, String otp, String newPassword) {
        // 1. Verify OTP
        String savedOtp = otpStorage.get(email);
        if (savedOtp == null || !savedOtp.equals(otp)) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        // 2. Get User and Update Password
        User user = userRepository.findByEmail(email).get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // 3. Clear the OTP so it can't be used again
        otpStorage.remove(email);
    }
}

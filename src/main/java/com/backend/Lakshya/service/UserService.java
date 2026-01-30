package com.backend.Lakshya.service;

import com.backend.Lakshya.dto.UserDTO;
import com.backend.Lakshya.mapper.UserMapper;
import com.backend.Lakshya.model.User;
import com.backend.Lakshya.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // 1. Tells Spring this is a Service bean
public class UserService {

    // 2. Define the dependencies as final fields
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 3. Create a constructor to inject these dependencies
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getUsers()
    {
        return userRepository.findAll().stream().map(UserMapper::toDTO).collect(Collectors.toList());
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
}
package com.backend.Lakshya.repository;

import com.backend.Lakshya.model.Role;
import com.backend.Lakshya.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    // Finds salespeople that belong ONLY to this specific owner
    List<User> findByRoleAndOwnerId(Role role, Long ownerId);
}

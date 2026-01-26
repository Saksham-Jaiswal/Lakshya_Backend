package com.backend.Lakshya.mapper;

import com.backend.Lakshya.dto.UserDTO;
import com.backend.Lakshya.model.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setContact(user.getContact());
        dto.setRole(user.getRole() != null ? user.getRole().name() : null);
        dto.setEmail(user.getEmail());
        return dto;
    }
}

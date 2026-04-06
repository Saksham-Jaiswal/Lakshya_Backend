package com.backend.Lakshya.dto;

public class UserDTO {
    private Long userId;
    private String name;
    private String contact;
    private String role;
    private Long ownerId;
    private String email;

    // No password here for security reasons
    // Getters & Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getRole() { return role; }

    public Long getOwnerId() {return ownerId;}

    public void setOwnerId(Long ownerId) {this.ownerId = ownerId;}

    public void setRole(String role) { this.role = role; }


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

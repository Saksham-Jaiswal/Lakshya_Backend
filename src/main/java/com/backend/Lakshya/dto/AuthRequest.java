// src/main/java/com/backend/Lakshya/dto/AuthRequest.java
package com.backend.Lakshya.dto;

public class AuthRequest {
    private String email;
    private String password;
    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
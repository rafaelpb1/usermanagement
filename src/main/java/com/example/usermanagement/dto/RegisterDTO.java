package com.example.usermanagement.dto;

import com.example.usermanagement.model.users.Role;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank(message = "Login is required")
        String login,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "Role is required")
        Role role) {
}

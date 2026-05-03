package com.example.usermanagement.dto;

import com.example.usermanagement.model.users.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
        @NotBlank(message = "Login is required")
        String login,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "Email is required")
        String email,
        @NotNull(message = "Role is required")
        Role role

) {
}

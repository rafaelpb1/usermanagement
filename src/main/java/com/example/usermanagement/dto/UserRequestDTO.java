package com.example.usermanagement.dto;

import com.example.usermanagement.model.users.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "Field required")
        @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
        String username,

        @NotBlank(message = "Field required")
        @Size(min = 4, message = "Password must be under 4 characters")
        String password,

        @NotNull(message = "Field required")
        Role role
) {
}

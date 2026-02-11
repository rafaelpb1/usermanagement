package com.example.usermanagement.dto;

import com.example.usermanagement.model.users.Role;

public record UserResponseDTO(
        Long id,
        String username,
        Role role,
        boolean enabled
) {
}

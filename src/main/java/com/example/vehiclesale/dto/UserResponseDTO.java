package com.example.vehiclesale.dto;

import com.example.vehiclesale.model.users.Role;

public record UserResponseDTO(
        Long id,
        String username,
        Role role,
        boolean enabled
) {
}

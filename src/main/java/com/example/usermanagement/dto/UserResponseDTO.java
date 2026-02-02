package com.example.usermanagement.dto;

import com.example.usermanagement.model.Role;

public record UserResponseDTO(
        Long id,
        String username,
        String password,
        Role role,
        Boolean enabled
) {
}

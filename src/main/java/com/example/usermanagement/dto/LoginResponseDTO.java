package com.example.usermanagement.dto;

public record LoginResponseDTO(
        String token,
        long expiresIn
) {
}

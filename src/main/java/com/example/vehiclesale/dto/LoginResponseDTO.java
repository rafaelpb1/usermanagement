package com.example.vehiclesale.dto;

public record LoginResponseDTO(
        String token,
        long expiresIn
) {
}

package com.example.usermanagement.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record CustomerResponseDTO(
        String document,
        String name,
        String phone,
        Instant created_at
) {
}

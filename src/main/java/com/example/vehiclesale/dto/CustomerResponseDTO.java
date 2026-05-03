package com.example.vehiclesale.dto;

import java.time.LocalDateTime;

public record CustomerResponseDTO(
        String document,
        String name,
        String phone,
        LocalDateTime created_at
) {
}


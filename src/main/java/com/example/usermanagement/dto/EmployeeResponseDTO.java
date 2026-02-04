package com.example.usermanagement.dto;

import java.time.Instant;

public record EmployeeResponseDTO(
        Long id,
        String name,
        String position,
        Instant created_at
) {
}

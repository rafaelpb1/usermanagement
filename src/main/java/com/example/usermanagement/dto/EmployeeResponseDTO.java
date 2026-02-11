package com.example.usermanagement.dto;

import java.time.LocalDateTime;

public record EmployeeResponseDTO(
        Long id,
        String name,
        String position,
        LocalDateTime created_at
) {
}

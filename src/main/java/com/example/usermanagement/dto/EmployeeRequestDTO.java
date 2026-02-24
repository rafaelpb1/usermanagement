package com.example.usermanagement.dto;

import jakarta.validation.constraints.NotBlank;

public record EmployeeRequestDTO(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Position is required")
        String position
) {
}

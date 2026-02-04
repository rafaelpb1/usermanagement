package com.example.usermanagement.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequestDTO(
        @NotBlank
        String document,
        @NotBlank
        String name,
        @NotBlank
        String phone
) {
}

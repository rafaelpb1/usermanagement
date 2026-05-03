package com.example.usermanagement.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerUpdateDTO(
        @NotBlank
        String name,
        String phone
) {
}

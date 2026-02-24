package com.example.usermanagement.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerRequestDTO(
        @NotBlank(message = "Document is required")
        @CPF(message = "Invalid CPF")
        String document,
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Phone is required")
        String phone
) {
}

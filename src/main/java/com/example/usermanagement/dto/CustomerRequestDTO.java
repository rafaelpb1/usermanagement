package com.example.usermanagement.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerRequestDTO(
        @NotBlank
        @CPF
        String document,
        @NotBlank
        String name,
        @NotBlank
        String phone
) {
}

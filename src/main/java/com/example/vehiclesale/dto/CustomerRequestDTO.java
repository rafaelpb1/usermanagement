package com.example.usermanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerRequestDTO(
        @NotBlank(message = "Document is required")
        @CPF(message = "Invalid CPF")
        @Schema(description = "Documento", example = "28378947025")
        String document,
        @NotBlank(message = "Name is required")
        @Schema(description = "Nome", example = "Rafael Luna")
        String name,
        @NotBlank(message = "Phone is required")
        @Schema(description = "Telefone", example = "(11) 91010-0000")
        String phone
) {
}

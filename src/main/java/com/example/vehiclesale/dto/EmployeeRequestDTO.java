package com.example.vehiclesale.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record EmployeeRequestDTO(
        @NotBlank(message = "Name is required")
        @Schema(description = "Nome", example = "Lucas Farias")
        String name,
        @NotBlank(message = "Position is required")
        @Schema(description = "Cargo", example = "Dev")
        String position
) {
}

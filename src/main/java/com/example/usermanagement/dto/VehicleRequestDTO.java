package com.example.usermanagement.dto;

import com.example.usermanagement.model.vehicle.VehicleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record VehicleRequestDTO(
        @NotBlank(message = "VIN is required")
        @Size(min = 17, max = 17, message = "VIN must be 17 characters long")
        @Schema(description = "Vin", example = "1FAFP34341W207183")
        String vin,
        @NotBlank(message = "License plate is required")
        @Schema(description = "Placa", example = "AAA-1A11")
        String licensePlate,
        @NotBlank(message = "Brand is required")
        @Schema(description = "Marca", example = "Toyota")
        String brand,
        @NotBlank(message = "Model is required")
        @Schema(description = "Modelo", example = "Corolla")
        String model,
        String color,
        @NotNull(message = "Year is required")
        @Schema(description = "Ano", example = "2025")
        Integer year,
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        @Schema(description = "Preço", example = "")
        BigDecimal price,
        VehicleStatus status
) {
}

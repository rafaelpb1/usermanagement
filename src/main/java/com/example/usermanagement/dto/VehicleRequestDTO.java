package com.example.usermanagement.dto;

import com.example.usermanagement.model.vehicle.VehicleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record VehicleRequestDTO(
        @NotBlank
        String vin,
        @NotBlank
        String licensePlate,
        @NotBlank
        String brand,
        @NotBlank
        String model,
        String color,
        @NotNull
        Integer year,
        @NotNull
        BigDecimal price,
        VehicleStatus status
) {
}

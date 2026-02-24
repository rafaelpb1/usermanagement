package com.example.usermanagement.dto;

import com.example.usermanagement.model.vehicle.VehicleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record VehicleRequestDTO(
        @NotBlank(message = "VIN is required")
        @Size(min = 17, max = 17, message = "VIN must be 17 characters long")
        String vin,
        @NotBlank(message = "License plate is required")
        String licensePlate,
        @NotBlank(message = "Brand is required")
        String brand,
        @NotBlank(message = "Model is required")
        String model,
        String color,
        @NotNull(message = "Year is required")
        Integer year,
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        BigDecimal price,
        @NotNull(message = "Status is required")
        VehicleStatus status
) {
}

package com.example.usermanagement.dto;

import com.example.usermanagement.model.vehicle.VehicleStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record VehicleUpdateDTO(
        String licensePlate,
        String brand,
        String model,
        String color,
        Integer year,
        @Positive(message = "Price must be greater than zero")
        @Digits(integer = 10, fraction = 2, message = "Price must have up to 10 integer digits and 2 decimal places")
        BigDecimal price,
        VehicleStatus status
) {
}

package com.example.usermanagement.dto;

import com.example.usermanagement.model.vehicle.VehicleStatus;

import java.math.BigDecimal;

public record VehicleUpdateDTO(
        String licensePlate,
        String brand,
        String model,
        String color,
        Integer year,
        BigDecimal price,
        VehicleStatus status
) {
}

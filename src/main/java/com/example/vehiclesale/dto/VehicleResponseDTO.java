package com.example.vehiclesale.dto;

import com.example.vehiclesale.model.vehicle.VehicleStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record VehicleResponseDTO(
        String vin,
        String licensePlate,
        String brand,
        String model,
        String color,
        Integer year,
        BigDecimal price,
        VehicleStatus status,
        Instant createdAt
) {
}

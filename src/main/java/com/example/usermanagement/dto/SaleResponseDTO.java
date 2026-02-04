package com.example.usermanagement.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record SaleResponseDTO(
        Long id,
        String vehicleModel,
        String customerName,
        String employeeName,
        BigDecimal saleValue,
        Instant saleDate

) {
}

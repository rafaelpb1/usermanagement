package com.example.vehiclesale.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleResponseDTO(
        Long id,
        String vehicleModel,
        String customerName,
        String employeeName,
        BigDecimal saleValue,
        LocalDateTime saleDate

) {
}

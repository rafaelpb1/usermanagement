package com.example.usermanagement.dto;

import java.math.BigDecimal;

public record SaleRequestDTO(
        String vehicleVin,
        String customerDocument,
        String employeeId,
        BigDecimal saleValue,
        String paymentMethod
) {
}

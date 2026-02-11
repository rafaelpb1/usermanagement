package com.example.usermanagement.dto;

import com.example.usermanagement.model.sale.PaymentMethod;

import java.math.BigDecimal;

public record SaleRequestDTO(
        String vehicleVin,
        String customerDocument,
        Long employeeId,
        BigDecimal saleValue,
        PaymentMethod paymentMethod
) {
}

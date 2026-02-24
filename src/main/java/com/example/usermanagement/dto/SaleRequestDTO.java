package com.example.usermanagement.dto;

import com.example.usermanagement.model.sale.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SaleRequestDTO(
        @NotBlank(message = "Vehicle VIN is required")
        String vehicleVin,
        @NotBlank(message = "Customer document is required")
        String customerDocument,
        @NotNull(message = "Employee ID is required")
        Long employeeId,
        @NotNull(message = "Sale value is required")
        @Positive(message = "Sale value must be greater than zero")
        BigDecimal saleValue,
        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod
) {
}

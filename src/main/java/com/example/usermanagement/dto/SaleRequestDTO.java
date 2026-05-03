package com.example.usermanagement.dto;

import com.example.usermanagement.model.sale.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SaleRequestDTO(
        @NotBlank(message = "Vehicle VIN is required")
        @Schema(description = "Vin", example = "1FAFP34341W207183")
        String vehicleVin,

        @NotBlank(message = "Customer document is required")
        @Schema(description = "Documento do comprador", example = "28378947025")
        String customerDocument,

        @NotNull(message = "Employee ID is required")
        @Schema(description = "ID do funcionário", example = "1")
        Long employeeId,

        @NotNull(message = "Sale value is required")
        @Positive(message = "Sale value must be greater than zero")
        @Digits(integer = 10, fraction = 2, message = "Sale value must have up to 10 integer digits and 2 decimal places")
        @Schema(description = "Valor da venda", example = "")
        BigDecimal saleValue,
        
        @NotNull(message = "Payment method is required")
        @Schema(description = "Método de pagamento", example = "PIX")
        PaymentMethod paymentMethod
) {
}

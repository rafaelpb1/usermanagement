package com.example.vehiclesale.controller;

import com.example.vehiclesale.exception.GlobalExceptionHandler;
import com.example.vehiclesale.model.vehicle.Vehicle;
import com.example.vehiclesale.service.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SaleControllerConcurrencyTest {

    private MockMvc mockMvc;

    @Mock
    private SaleService saleService;

    @InjectMocks
    private SaleController saleController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(saleController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void deveRetornar409QuandoHouverConflitoDeConcorrenciaNaVenda() throws Exception {
        when(saleService.create(any())).thenThrow(
                new ObjectOptimisticLockingFailureException(Vehicle.class, "1FAFP34341W207183")
        );

        String body = """
                {
                  "vehicleVin": "1FAFP34341W207183",
                  "customerDocument": "28378947025",
                  "employeeId": 1,
                  "saleValue": 120000.00,
                  "paymentMethod": "PIX"
                }
                """;

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").value("Conflito de concorrencia: o veículo foi alterado por outra operação. Atualize e tente novamente."));
    }
}

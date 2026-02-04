package com.example.usermanagement.model.vehicle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vehicle {
    private String vin;
    private String licensePate;
    private String brand;
    private String model;
    private String color;
    private Integer year;
    @Column(name = "price", precision = 12, scale = 2)
    private BigDecimal price;
    private String status;
    private Instant created_at;
}

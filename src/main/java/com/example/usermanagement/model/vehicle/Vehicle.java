package com.example.usermanagement.model.vehicle;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "vehicles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vehicle {
    @Id
    @Column(name = "vin", length = 17)
    private String vin;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "color", nullable = true)
    private String color;

    @Column(name = "model_year", nullable = false)
    private Integer year;

    @NotNull
    @Column(name = "price", precision = 12, scale = 2, nullable = false)
    @Digits(integer = 10, fraction = 2)
    @Positive
    private BigDecimal price;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VehicleStatus status = VehicleStatus.AVAILABLE;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;
}

package com.example.usermanagement.model.sale;

import com.example.usermanagement.model.customers.Customer;
import com.example.usermanagement.model.employee.Employee;
import com.example.usermanagement.model.vehicle.Vehicle;
import jakarta.persistence.*;
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
public class Sale {
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vin")
    private Vehicle vehicle_vin;

    @ManyToOne
    @JoinColumn(name = "document")
    private Customer customer_id;

    @ManyToOne
    @JoinColumn(name = "id")
    private Employee employee_id;

    private Instant sale_date;

    @Column(precision = 12, scale = 2)
    private BigDecimal value;
}

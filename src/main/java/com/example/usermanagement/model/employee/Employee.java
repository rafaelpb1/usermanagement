package com.example.usermanagement.model.employee;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {
    private Long id;
    private String name;
    private String position;
    private Instant created_at;
}

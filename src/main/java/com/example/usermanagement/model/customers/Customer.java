package com.example.usermanagement.model.customers;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {

    @Id
    @Column(nullable = false)
    private String document;

    @Column(nullable = false)
    private String name;

    private String phone;

    @CreationTimestamp
    private Instant created_at;
}

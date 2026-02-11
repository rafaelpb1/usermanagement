package com.example.usermanagement.controller;

import com.example.usermanagement.dto.SaleRequestDTO;
import com.example.usermanagement.dto.SaleResponseDTO;
import com.example.usermanagement.exception.ErrorResponse;
import com.example.usermanagement.repository.CustomerRepository;
import com.example.usermanagement.repository.EmployeeRepository;
import com.example.usermanagement.repository.VehicleRepository;
import com.example.usermanagement.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody SaleRequestDTO dto) {

        Optional<SaleResponseDTO> result = service.create(dto);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.responseDefault("Employee, Customer or Vehicle not found"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.get());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteByName(@PathVariable Long id) {
        boolean deleted = service.deleteById(id);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.responseDefault("Sale not found"));
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<SaleResponseDTO>> listAll() {
        Optional<List<SaleResponseDTO>> result = service.listAll();

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok(result.get());
    }
}

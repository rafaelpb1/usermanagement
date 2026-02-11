package com.example.usermanagement.controller;

import com.example.usermanagement.dto.CustomerRequestDTO;
import com.example.usermanagement.dto.CustomerResponseDTO;
import com.example.usermanagement.exception.ErrorResponse;
import com.example.usermanagement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createCustomer(@RequestBody CustomerRequestDTO dto) {
        Optional<CustomerResponseDTO> result = service.create(dto);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ErrorResponse.responseDefault("Already exists customer registered with this document."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.get());
    }

    @DeleteMapping("/{document}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteCustomerByDocument(@PathVariable String document) {
        Optional<Void> result = service.deleteByDocument(document);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.responseDefault("Customer not found"));
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<CustomerResponseDTO>> listAllCustomers() {
        Optional<List<CustomerResponseDTO>> result = service.listAll();

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(result.get());
    }
}

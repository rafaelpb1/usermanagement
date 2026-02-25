package com.example.usermanagement.controller;

import com.example.usermanagement.dto.CustomerRequestDTO;
import com.example.usermanagement.dto.CustomerResponseDTO;
import com.example.usermanagement.dto.CustomerUpdateDTO;
import com.example.usermanagement.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @DeleteMapping("/{document}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponseDTO> deleteCustomerByDocument(@PathVariable String document) {
        service.deleteByDocument(document);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<CustomerResponseDTO>> listAllCustomers() {
        List<CustomerResponseDTO> result = service.listAll();

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{document}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable String document,
                                                              @RequestBody @Valid CustomerUpdateDTO dto) {
        CustomerResponseDTO response = service.updateCustomer(document, dto);
        return ResponseEntity.ok().body(response);
    }
}

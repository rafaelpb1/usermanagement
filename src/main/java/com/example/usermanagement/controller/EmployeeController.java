package com.example.usermanagement.controller;

import com.example.usermanagement.dto.CustomerResponseDTO;
import com.example.usermanagement.dto.EmployeeRequestDTO;
import com.example.usermanagement.dto.EmployeeResponseDTO;
import com.example.usermanagement.exception.ErrorResponse;
import com.example.usermanagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<EmployeeResponseDTO>> listAllEmployees() {
        List<EmployeeResponseDTO> result = service.listALl();

        return ResponseEntity.ok().body(result);
    }
}

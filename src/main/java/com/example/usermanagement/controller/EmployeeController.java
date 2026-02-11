package com.example.usermanagement.controller;

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
    public ResponseEntity<Object> createEmployee(@RequestBody EmployeeRequestDTO dto) {
        Optional<EmployeeResponseDTO> result = service.create(dto);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ErrorResponse
                            .of(HttpStatus.CONFLICT,"Already exists employee registered with this name."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.get());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteEmployeeById(@PathVariable Long id) {
        Optional<Void> result = service.deleteById(id);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.of(HttpStatus.NOT_FOUND,"Employee not found"));
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<EmployeeResponseDTO>> listAllEmployees() {
        List<EmployeeResponseDTO> result = service.listALl();

        return ResponseEntity.ok().body(result);
    }
}

package com.example.usermanagement.controller;

import com.example.usermanagement.dto.VehicleRequestDTO;
import com.example.usermanagement.dto.VehicleResponseDTO;
import com.example.usermanagement.exception.ErrorResponse;
import com.example.usermanagement.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createVehicle(@RequestBody @Valid VehicleRequestDTO dto) {
        Optional<VehicleResponseDTO> result = service.create(dto);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ErrorResponse.of(HttpStatus.CONFLICT,"Already exists customer registered with this VIN."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.get());
    }

    @DeleteMapping("/{vin}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteVehicleByVin(@PathVariable String vin) {
        Optional<Void> result = service.deleteByVIN(vin);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.of(HttpStatus.NOT_FOUND,"Vehicle not found."));
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<VehicleResponseDTO>> listAll() {
        List<VehicleResponseDTO> result = service.listAllVehicles();

        return ResponseEntity.ok(result);
    }
}

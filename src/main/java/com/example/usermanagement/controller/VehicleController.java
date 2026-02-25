package com.example.usermanagement.controller;

import com.example.usermanagement.dto.EmployeeResponseDTO;
import com.example.usermanagement.dto.VehicleRequestDTO;
import com.example.usermanagement.dto.VehicleResponseDTO;
import com.example.usermanagement.dto.VehicleUpdateDTO;
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
    public ResponseEntity<VehicleResponseDTO> createVehicle(@RequestBody @Valid VehicleRequestDTO dto) {
        VehicleResponseDTO response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{vin}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteVehicleByVin(@PathVariable String vin) {
        service.deleteByVIN(vin);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<VehicleResponseDTO>> listAllVehicles() {
        List<VehicleResponseDTO> response = service.listAllVehicles();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{vin}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(@PathVariable String vin,
                                                            @RequestBody @Valid VehicleUpdateDTO dto ) {
        VehicleResponseDTO response = service.updateVehicle(vin, dto);
        return ResponseEntity.ok(response);
    }
}

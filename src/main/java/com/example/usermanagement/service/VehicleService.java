package com.example.usermanagement.service;

import com.example.usermanagement.dto.VehicleRequestDTO;
import com.example.usermanagement.dto.VehicleResponseDTO;
import com.example.usermanagement.mappers.VehicleMapper;
import com.example.usermanagement.model.vehicle.Vehicle;
import com.example.usermanagement.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository repository;
    private final VehicleMapper mapper;

    @Transactional
    public VehicleResponseDTO create(VehicleRequestDTO dto) {
        if (dto.vin() != null && repository.existsById(dto.vin())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Already exists car registered with this VIN.");
        }

        Vehicle vehicle = mapper.toEntity(dto);
        Vehicle saved = repository.save(vehicle);

        return mapper.toDTO(saved);
    }
}

package com.example.usermanagement.service;

import com.example.usermanagement.dto.VehicleRequestDTO;
import com.example.usermanagement.dto.VehicleResponseDTO;
import com.example.usermanagement.mappers.VehicleMapper;
import com.example.usermanagement.model.vehicle.Vehicle;
import com.example.usermanagement.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository repository;
    private final VehicleMapper mapper;

    @Transactional
    public Optional<VehicleResponseDTO> create(VehicleRequestDTO dto) {
        if (dto.vin() != null && repository.existsById(dto.vin())) {
            return Optional.empty();
        }

        Vehicle vehicle = mapper.toEntity(dto);
        Vehicle saved = repository.save(vehicle);

        return Optional.of(mapper.toDTO(saved));
    }

    @Transactional
    public Optional<Void> deleteByVIN(String vin) {
        Optional<Vehicle> result = repository.findById(vin);

        if (result.isEmpty()) return Optional.empty();

        repository.delete(result.get());

        return Optional.ofNullable(null);
    }

    public List<VehicleResponseDTO> listAllVehicles() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();

    }
}

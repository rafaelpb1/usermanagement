package com.example.usermanagement.service;

import com.example.usermanagement.dto.VehicleRequestDTO;
import com.example.usermanagement.dto.VehicleResponseDTO;
import com.example.usermanagement.dto.VehicleUpdateDTO;
import com.example.usermanagement.exception.VehicleAlreadyRegisteredException;
import com.example.usermanagement.exception.VehicleNotFoundException;
import com.example.usermanagement.mappers.VehicleMapper;
import com.example.usermanagement.model.vehicle.Vehicle;
import com.example.usermanagement.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository repository;
    private final VehicleMapper mapper;

    @Transactional
    public VehicleResponseDTO create(VehicleRequestDTO dto) {
        if (dto.vin() != null && repository.existsById(dto.vin())) {
            throw new VehicleAlreadyRegisteredException("Vehicle already registered at system.");
        }

        Vehicle vehicle = mapper.toEntity(dto);
        Vehicle saved = repository.saveAndFlush(vehicle);

        return mapper.toDTO(saved);
    }

    @Transactional
    public void deleteByVIN(String vin) {
        Vehicle vehicle = repository.findById(vin).orElseThrow(() ->
                new VehicleNotFoundException("VIN not found."));

        repository.delete(vehicle);
    }

    public List<VehicleResponseDTO> listAllVehicles() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();

    }

    @Transactional
    public VehicleResponseDTO updateVehicle(String vin, VehicleUpdateDTO dto) {
        Vehicle vehicle = repository.findById(vin).orElseThrow(() ->
                new VehicleNotFoundException("Vehicle not found."));

        mapper.updateEntityFromDto(dto, vehicle);
        Vehicle saved = repository.save(vehicle);

        return mapper.toDTO(saved);
    }
}

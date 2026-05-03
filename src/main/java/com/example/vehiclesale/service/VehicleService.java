package com.example.vehiclesale.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.vehiclesale.dto.VehicleRequestDTO;
import com.example.vehiclesale.dto.VehicleResponseDTO;
import com.example.vehiclesale.dto.VehicleUpdateDTO;
import com.example.vehiclesale.exception.VehicleAlreadyRegisteredException;
import com.example.vehiclesale.exception.VehicleNotFoundException;
import com.example.vehiclesale.mappers.VehicleMapper;
import com.example.vehiclesale.model.vehicle.Vehicle;
import com.example.vehiclesale.repository.VehicleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

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

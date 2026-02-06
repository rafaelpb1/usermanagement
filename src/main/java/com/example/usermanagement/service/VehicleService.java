package com.example.usermanagement.service;

import com.example.usermanagement.dto.VehicleRequestDTO;
import com.example.usermanagement.dto.VehicleResponseDTO;
import com.example.usermanagement.exception.NotFoundVinException;
import com.example.usermanagement.exception.RoleBusinessException;
import com.example.usermanagement.mappers.VehicleMapper;
import com.example.usermanagement.model.vehicle.Vehicle;
import com.example.usermanagement.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository repository;
    private final VehicleMapper mapper;

    @Transactional
    public VehicleResponseDTO create(VehicleRequestDTO dto) {
        if (dto.vin() != null && repository.existsById(dto.vin())) {
            throw new RoleBusinessException(HttpStatus.CONFLICT,
                    "Already exists car registered with this VIN.");
        }

        Vehicle vehicle = mapper.toEntity(dto);
        Vehicle saved = repository.save(vehicle);

        return mapper.toDTO(saved);
    }

    @Transactional
    public void deleteByVIN(String vin) {
        var vinexclude = repository.findById(vin)
                .orElseThrow( () -> new NotFoundVinException(HttpStatus.NOT_FOUND, "Not found VIN."));

        repository.delete(vinexclude);
    }

    public List<VehicleResponseDTO> listAllVehicles() {
        List<Vehicle> all = repository.findAll();
        List<VehicleResponseDTO> list = all.stream().map(mapper::toDTO).toList();

        if (all.isEmpty()) throw new ResponseStatusException
                (HttpStatus.NO_CONTENT, "No vehicle found.");

        return list;
    }
}

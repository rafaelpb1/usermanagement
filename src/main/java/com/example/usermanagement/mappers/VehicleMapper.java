package com.example.usermanagement.mappers;

import com.example.usermanagement.dto.VehicleRequestDTO;
import com.example.usermanagement.dto.VehicleResponseDTO;
import com.example.usermanagement.model.vehicle.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    Vehicle toEntity(VehicleRequestDTO dto);

    VehicleResponseDTO toDTO(Vehicle entity);
}

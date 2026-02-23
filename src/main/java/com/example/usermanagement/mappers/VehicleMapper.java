package com.example.usermanagement.mappers;

import com.example.usermanagement.dto.VehicleRequestDTO;
import com.example.usermanagement.dto.VehicleResponseDTO;
import com.example.usermanagement.model.vehicle.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    Vehicle toEntity(VehicleRequestDTO dto);

    @Mapping(target = "status", ignore = true)
    VehicleResponseDTO toDTO(Vehicle entity);
}

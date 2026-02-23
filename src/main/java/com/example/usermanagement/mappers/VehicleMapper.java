package com.example.usermanagement.mappers;

import com.example.usermanagement.dto.VehicleRequestDTO;
import com.example.usermanagement.dto.VehicleResponseDTO;
import com.example.usermanagement.model.vehicle.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "status", ignore = true)
    Vehicle toEntity(VehicleRequestDTO dto);

    VehicleResponseDTO toDTO(Vehicle entity);
}

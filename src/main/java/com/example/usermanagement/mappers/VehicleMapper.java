package com.example.usermanagement.mappers;

import com.example.usermanagement.dto.VehicleRequestDTO;
import com.example.usermanagement.dto.VehicleResponseDTO;
import com.example.usermanagement.dto.VehicleUpdateDTO;
import com.example.usermanagement.model.vehicle.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "status", ignore = true)
    Vehicle toEntity(VehicleRequestDTO dto);

    VehicleResponseDTO toDTO(Vehicle entity);

    @Mapping(target = "vin", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(VehicleUpdateDTO dto, @MappingTarget Vehicle entity);
}

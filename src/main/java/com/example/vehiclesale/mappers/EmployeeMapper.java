package com.example.vehiclesale.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.vehiclesale.dto.EmployeeRequestDTO;
import com.example.vehiclesale.dto.EmployeeResponseDTO;
import com.example.vehiclesale.model.employee.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeRequestDTO dto);

    @Mapping(source = "createdAt", target = "created_at")
    EmployeeResponseDTO toDTO(Employee entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(EmployeeRequestDTO dto, @MappingTarget Employee entity);
}

package com.example.usermanagement.mappers;

import com.example.usermanagement.dto.EmployeeRequestDTO;
import com.example.usermanagement.dto.EmployeeResponseDTO;
import com.example.usermanagement.model.employee.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeRequestDTO dto);

    @Mapping(source = "createdAt", target = "created_at")
    EmployeeResponseDTO toDTO(Employee entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(EmployeeRequestDTO dto, @MappingTarget Employee entity);
}

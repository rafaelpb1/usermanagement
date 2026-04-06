package com.example.usermanagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.usermanagement.dto.CustomerRequestDTO;
import com.example.usermanagement.dto.CustomerResponseDTO;
import com.example.usermanagement.dto.CustomerUpdateDTO;
import com.example.usermanagement.model.customers.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerRequestDTO dto);

    @Mapping(source = "createdAt", target = "created_at")
    CustomerResponseDTO toDTO(Customer entity);

    @Mapping(target = "document", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(CustomerUpdateDTO dto, @MappingTarget Customer entity);
}

package com.example.vehiclesale.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.vehiclesale.dto.CustomerRequestDTO;
import com.example.vehiclesale.dto.CustomerResponseDTO;
import com.example.vehiclesale.dto.CustomerUpdateDTO;
import com.example.vehiclesale.model.customers.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerRequestDTO dto);

    @Mapping(source = "createdAt", target = "created_at")
    CustomerResponseDTO toDTO(Customer entity);

    @Mapping(target = "document", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(CustomerUpdateDTO dto, @MappingTarget Customer entity);
}

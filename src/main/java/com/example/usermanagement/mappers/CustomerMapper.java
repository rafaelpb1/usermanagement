package com.example.usermanagement.mappers;

import com.example.usermanagement.dto.CustomerRequestDTO;
import com.example.usermanagement.dto.CustomerResponseDTO;
import com.example.usermanagement.model.customers.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerRequestDTO dto);

    @Mapping(source = "createdAt", target = "created_at")
    CustomerResponseDTO toDTO(Customer entity);

}

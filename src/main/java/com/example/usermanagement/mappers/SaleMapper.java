package com.example.usermanagement.mappers;

import com.example.usermanagement.dto.SaleRequestDTO;
import com.example.usermanagement.dto.SaleResponseDTO;
import com.example.usermanagement.model.sale.Sale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    Sale toEntity(SaleRequestDTO dto);

    SaleResponseDTO toDTO(Sale entity);
}

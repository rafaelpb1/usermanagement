package com.example.vehiclesale.mappers;

import com.example.vehiclesale.dto.RegisterDTO;
import com.example.vehiclesale.model.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(RegisterDTO dto);
}

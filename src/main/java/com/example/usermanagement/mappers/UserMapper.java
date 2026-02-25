package com.example.usermanagement.mappers;

import com.example.usermanagement.dto.RegisterDTO;
import com.example.usermanagement.dto.UserRequestDTO;
import com.example.usermanagement.dto.UserResponseDTO;
import com.example.usermanagement.model.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(RegisterDTO dto);
}

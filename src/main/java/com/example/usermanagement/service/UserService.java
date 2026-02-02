package com.example.usermanagement.service;

import com.example.usermanagement.dto.UserRequestDTO;
import com.example.usermanagement.dto.UserResponseDTO;
import com.example.usermanagement.mappers.UserMapper;
import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Transactional
    public UserResponseDTO save(UserRequestDTO dto) {
        User user = mapper.toEntity(dto);
        User save = repository.save(user);

        return mapper.toDTO(user);

    }

    @Transactional
    public void delete(Long id) {
        var user = repository.findById(id).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        repository.delete(user);
    }

    public List<UserResponseDTO> list() {
        List<User> all = repository.findAll();

        return all.stream()
                .map(mapper::toDTO)
                .toList();
    }

//    public UserResponseDTO findById()
}

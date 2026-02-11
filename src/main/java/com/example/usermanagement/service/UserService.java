package com.example.usermanagement.service;

import com.example.usermanagement.dto.UserRequestDTO;
import com.example.usermanagement.dto.UserResponseDTO;
import com.example.usermanagement.mappers.UserMapper;
import com.example.usermanagement.model.users.User;
import com.example.usermanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Transactional
    public UserResponseDTO save(UserRequestDTO dto) {
        if (repository.findByUsername(dto.username()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");

        User user = mapper.toEntity(dto);
        user.setPassword(encoder.encode(dto.password()));

        User save = repository.save(user);
        return mapper.toDTO(save);

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

    public UserResponseDTO ById(Long id) {
        User UserById = repository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        return mapper.toDTO(UserById);
    }

    public UserResponseDTO findByUsername(String username) {
        User user = repository.findByUsername(username)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        return mapper.toDTO(user);
    }

//    public UserResponseDTO findById()
}

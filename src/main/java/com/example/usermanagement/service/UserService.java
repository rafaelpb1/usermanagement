package com.example.usermanagement.service;

import com.example.usermanagement.dto.UserRequestDTO;
import com.example.usermanagement.dto.UserResponseDTO;
import com.example.usermanagement.exception.UserNotExists;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Transactional
    public UserResponseDTO create(UserRequestDTO dto) {
        if (repository.findByUsername(dto.username()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        User user = mapper.toEntity(dto);
        user.setPassword(encoder.encode(dto.password()));

        User saved = repository.save(user);
        return mapper.toDTO(saved);

    }

    @Transactional
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new UserNotExists("User not exists"));

        repository.deleteById(id);
    }

    public List<UserResponseDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public Optional<UserResponseDTO> listById(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public UserResponseDTO findByUsername(String username) {
        User user = repository.findByUsername(username)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        return mapper.toDTO(user);
    }

//    public UserResponseDTO findById()
}

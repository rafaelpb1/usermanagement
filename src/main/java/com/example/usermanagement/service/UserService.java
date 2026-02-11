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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Transactional
    public Optional<UserResponseDTO> create(UserRequestDTO dto) {
        if (repository.findByUsername(dto.username()).isPresent()) {
            return Optional.empty();
        }

        User user = mapper.toEntity(dto);
        user.setPassword(encoder.encode(dto.password()));

        User saved = repository.save(user);
        return Optional.of(mapper.toDTO(saved));

    }

    @Transactional
    public Optional<Void> delete(Long id) {
        Optional<User> user = repository.findById(id);

        if (user.isEmpty()) return Optional.empty();

        repository.delete(user.get());

        return Optional.ofNullable(null);
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

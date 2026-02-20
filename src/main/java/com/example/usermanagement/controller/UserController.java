package com.example.usermanagement.controller;

import com.example.usermanagement.dto.UserRequestDTO;
import com.example.usermanagement.dto.UserResponseDTO;
import com.example.usermanagement.exception.ErrorResponse;
import com.example.usermanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO dto) {
        UserResponseDTO userResponseDTO = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserResponseDTO>> listAllUsers() {
        List<UserResponseDTO> userResponseDTO = service.listAll();
        return ResponseEntity.ok().body(userResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> searchUserById(@PathVariable Long id) {
        Optional<UserResponseDTO> userResponseDTO = service.listById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        service.delete(id);
    }
}

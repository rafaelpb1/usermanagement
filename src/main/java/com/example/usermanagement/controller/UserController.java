package com.example.usermanagement.controller;

import com.example.usermanagement.dto.UserRequestDTO;
import com.example.usermanagement.dto.UserResponseDTO;
import com.example.usermanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.save(dto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserResponseDTO>> listAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> searchById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.ById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

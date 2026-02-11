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
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserRequestDTO dto) {
        Optional<UserResponseDTO> result = service.create(dto);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ErrorResponse.of(HttpStatus.CONFLICT,
                            "Already user was registered at system."));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result.get());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserResponseDTO>> listAllUsers() {
        List<UserResponseDTO> result = service.listAll();

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> searchUserById(@PathVariable Long id) {
        Optional<UserResponseDTO> result = service.listById(id);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ErrorResponse.of(HttpStatus.OK, "User not found"));
        }

        return ResponseEntity.ok(result.get());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        Optional<Void> userDeleted = service.delete(id);

        if (userDeleted.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.of(HttpStatus.NOT_FOUND, "User not found."));
        }

        return ResponseEntity.noContent().build();
    }
}

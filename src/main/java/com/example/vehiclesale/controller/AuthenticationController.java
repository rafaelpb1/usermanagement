package com.example.vehiclesale.controller;

import com.example.vehiclesale.dto.LoginRequestDTO;
import com.example.vehiclesale.dto.RegisterDTO;
import com.example.vehiclesale.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDTO dto) {
        return ResponseEntity.ok(service.login(dto));
    }

    @PostMapping("/register")
    public ResponseEntity register (@RequestBody @Valid RegisterDTO dto) {
        service.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

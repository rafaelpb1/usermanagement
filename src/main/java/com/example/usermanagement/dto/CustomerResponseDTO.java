package com.example.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDateTime;

public record CustomerResponseDTO(
        String document,
        String name,
        String phone,
        LocalDateTime created_at
) {
}


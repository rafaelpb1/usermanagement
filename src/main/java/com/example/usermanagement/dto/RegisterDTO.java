package com.example.usermanagement.dto;

import com.example.usermanagement.model.users.Role;

public record RegisterDTO(String login, String password, Role role) {
}

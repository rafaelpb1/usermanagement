package com.example.usermanagement.model.users;

import lombok.Getter;

public enum Role {
    ADMIN("admin"),
    USER("user");

    @Getter
    private String role;

    Role(String role) {
        this.role = role;
    }

}

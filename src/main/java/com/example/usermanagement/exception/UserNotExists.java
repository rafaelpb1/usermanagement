package com.example.usermanagement.exception;

public class UserNotExists extends RuntimeException {
    public UserNotExists(String message) {
        super(message);
    }
}

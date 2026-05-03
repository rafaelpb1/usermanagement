package com.example.vehiclesale.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LoginAlreadyExists extends RuntimeException {
    public LoginAlreadyExists(String message) {
        super(message);
    }
}

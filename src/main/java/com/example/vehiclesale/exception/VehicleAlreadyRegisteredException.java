package com.example.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class VehicleAlreadyRegisteredException extends RuntimeException {
    public VehicleAlreadyRegisteredException(String message) {
        super(message);
    }
}

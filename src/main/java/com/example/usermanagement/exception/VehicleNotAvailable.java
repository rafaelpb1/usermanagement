package com.example.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class VehicleNotAvailable extends RuntimeException {
    public VehicleNotAvailable(String message) {
        super(message);
    }
}

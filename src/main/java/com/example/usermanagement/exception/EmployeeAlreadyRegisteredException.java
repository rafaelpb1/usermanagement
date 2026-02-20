package com.example.usermanagement.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@ResponseStatus(HttpStatus.CONFLICT)
public class EmployeeAlreadyRegisteredException extends RuntimeException {
    public EmployeeAlreadyRegisteredException(String message) {
        super(message);
    }
}

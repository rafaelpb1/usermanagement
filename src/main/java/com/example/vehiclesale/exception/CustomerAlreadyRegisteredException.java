package com.example.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ResponseStatus(HttpStatus.CONFLICT)
public class CustomerAlreadyRegisteredException extends RuntimeException {

    public CustomerAlreadyRegisteredException(String message) {
        super(message);
    }
}

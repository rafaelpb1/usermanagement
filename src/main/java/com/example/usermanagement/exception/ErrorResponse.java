package com.example.usermanagement.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponse(int status,
                            String message,
                            List<ErrorField> errors) {

    public static ErrorResponse responseDefault(String message) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }
}

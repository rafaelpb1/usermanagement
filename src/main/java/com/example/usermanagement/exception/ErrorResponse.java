package com.example.usermanagement.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponse(
        int status,
        String message,
        List<ErrorField> errors) {

    public static ErrorResponse of(HttpStatus status, String message) {
        return new ErrorResponse(
                status.value(),
                message,
                List.of());
    }

    public static ErrorResponse of(HttpStatus status, String message, List<ErrorField> errors) {
        return new ErrorResponse(
                status.value(),
                message,
                errors
        );
    }

}

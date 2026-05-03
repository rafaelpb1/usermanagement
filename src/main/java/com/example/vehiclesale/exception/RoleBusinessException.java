package com.example.vehiclesale.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
public class RoleBusinessException extends RuntimeException {

    @Getter
    private final HttpStatus status;

    public RoleBusinessException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}

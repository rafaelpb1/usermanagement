package com.example.vehiclesale.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class NotFoundVinException extends RuntimeException {

    @Getter
    private final HttpStatus status;

    public NotFoundVinException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}

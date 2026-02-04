package com.example.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleValidation(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErrorField> listErrors = fieldErrors.stream()
                .map(fe -> new ErrorField(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Error validation",
                listErrors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse HandleJsonInvalid(HttpMessageNotReadableException e) {
        return new ErrorResponse(422,
                "Error validation",
                List.of(new ErrorField("role", "Invalid value.")));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDenied(RuntimeException e) {
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                "Access Denied.",
                List.of());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleErrorGenericException(Exception e) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unexpected error.",
                List.of());
    }
}

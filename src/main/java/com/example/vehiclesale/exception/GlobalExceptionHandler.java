package com.example.vehiclesale.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.OptimisticLockException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErrorField> listErrors = fieldErrors.stream()
                .map(fe -> new ErrorField(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                listErrors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse HandleJsonInvalid(HttpMessageNotReadableException e) {
        return new ErrorResponse(400,
                "Error Malformed or invalid request body",
                List.of(new ErrorField(
                        "requestBody",
                        "Invalid JSON format or invalid enum value")));
    }

    @ExceptionHandler(CustomerAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCustomerAlreadyRegistered(CustomerAlreadyRegisteredException e) {
        return new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCustomerNotFound(CustomerNotFoundException e) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(RoleBusinessException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleBusinessRole(RoleBusinessException e) {
        return new ErrorResponse(e.getStatus().value(),
                e.getMessage(),
                List.of());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDenied(RuntimeException e) {
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                "Access Denied.",
                List.of());
    }

    @ExceptionHandler({ObjectOptimisticLockingFailureException.class, OptimisticLockException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleOptimisticLocking(RuntimeException e) {
        return new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflito de concorrencia: o veículo foi alterado por outra operação. Atualize e tente novamente.",
                List.of()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleErrorGenericException(Exception e) {

        e.printStackTrace();
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unexpected internal server error.",
                List.of());
    }
}

package com.eaglebank.api.config;

import com.eaglebank.api.model.dto.response.ErrorResponse;
import com.eaglebank.api.validation.exception.HttpRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(HttpRuntimeException ex,
            HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), ex.getStatusCode());
    }

    /** 
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(CommonException ex,
            HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), ex.getStatusCode());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(ValidationException ex,
            HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), ex.getStatusCode());
    }
*/

@   ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleGeneric(NoResourceFoundException ex,
            HttpServletRequest request) {
        return buildErrorResponse("resource not found",
                HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex,
            HttpServletRequest request) {
                System.out.println(ex);

        return buildErrorResponse("An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, int statusCode) {
        ErrorResponse response = new ErrorResponse().setMessage(message);
        return ResponseEntity.status(statusCode).body(response);
    }
}

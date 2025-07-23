package com.eaglebank.api.config;

import com.eaglebank.api.model.dto.response.ErrorResponse;
import com.eaglebank.api.validation.exception.HttpRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Global exception handler for REST controllers.
 * <p>
 * Handles application-specific and generic exceptions, returning a consistent error response
 * structure for all API endpoints.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles custom {@link HttpRuntimeException} exceptions thrown by the application.
     *
     * @param ex      the exception thrown
     * @param request the HTTP request
     * @return a ResponseEntity containing the error message and status code
     */
    @ExceptionHandler(HttpRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(HttpRuntimeException ex,
            HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), ex.getStatusCode());
    }

    /**
     * Handles {@link NoResourceFoundException} for missing resources (404 errors).
     *
     * @param ex      the exception thrown
     * @param request the HTTP request
     * @return a ResponseEntity with a "resource not found" message and 404 status
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleGeneric(NoResourceFoundException ex,
            HttpServletRequest request) {
        return buildErrorResponse("resource not found",
                HttpStatus.NOT_FOUND.value());
    }

    /**
     * Handles all uncaught exceptions, returning a generic error message and 500 status.
     *
     * @param ex      the exception thrown
     * @param request the HTTP request
     * @return a ResponseEntity with a generic error message and 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex,
            HttpServletRequest request) {
        System.out.println(ex);
        return buildErrorResponse("An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    /**
     * Helper method to build a consistent error response.
     *
     * @param message    the error message
     * @param statusCode the HTTP status code
     * @return a ResponseEntity containing the error response
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, int statusCode) {
        ErrorResponse response = new ErrorResponse().setMessage(message);
        return ResponseEntity.status(statusCode).body(response);
    }
}

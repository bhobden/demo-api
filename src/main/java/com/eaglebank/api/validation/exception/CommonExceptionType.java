package com.eaglebank.api.validation.exception;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;

public enum CommonExceptionType {
    UNKNOWN_ERROR("Server Error", HTTP_INTERNAL_ERROR), 
    INVALID_JWT("Invalid Token", HTTP_FORBIDDEN), 
    EXPIRED_JWT("Expired Token", HTTP_FORBIDDEN);

    private final String message;
    private final Integer statusCode;

    CommonExceptionType(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}

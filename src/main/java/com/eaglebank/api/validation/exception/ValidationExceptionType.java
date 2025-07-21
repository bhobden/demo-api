package com.eaglebank.api.validation.exception;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CONFLICT;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

public enum ValidationExceptionType {
    AUTH_ACCOUNT_NOT_FOUND("Account not found", HTTP_UNAUTHORIZED),
    AUTH_INVALID_CREDENTIALS("Invalid credentials", HTTP_UNAUTHORIZED),
    ID_ALREADY_EXISTS("Id already exits", HTTP_CONFLICT), 
    ID_NOT_FOUND("Invalid login details", HTTP_UNAUTHORIZED), 
    AUTH_UNAUTHORIZED("You are unable to access this user", HTTP_UNAUTHORIZED), 
    AUTH_INVALID_USERID("Invalid user id credentials", HTTP_BAD_REQUEST),
    AUTH_INVALID_PASSWORD("Invalid password credentials", HTTP_BAD_REQUEST), 
    USER_INVALID_NAME("Invalid name passed", HTTP_BAD_REQUEST), 
    USER_INVALID_EMAIL("Invalid email passed", HTTP_BAD_REQUEST), 
    USER_INVALID_ADDRESS("Invalid address passed", HTTP_BAD_REQUEST), 
    USER_INVALID_PHONE_NUMBER("Invalid phone number", HTTP_BAD_REQUEST), 
    AUTH_INVALID_USER("Invalid password credentials", HTTP_BAD_REQUEST);

    private final String message;
    private final Integer statusCode;

    ValidationExceptionType(String message, Integer statusCode) {
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

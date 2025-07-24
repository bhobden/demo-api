package com.eaglebank.api.validation.exception;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CONFLICT;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static com.eaglebank.api.validation.exception.HttpRuntimeException.HTTP_UNPROCESSABLE_ENTITY;

public enum ValidationExceptionType {
    AUTH_ACCOUNT_NOT_FOUND("Account not found", HTTP_NOT_FOUND),
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
    AUTH_INVALID_USER("Invalid password credentials", HTTP_BAD_REQUEST), 
    AUTH_USER_HAS_ACCOUNTS("User has associated accounts", HTTP_CONFLICT), 
    ACCOUNT_NAME_INVALID("Invalid account name", HTTP_BAD_REQUEST), 
    TRANSACTION_CURRENCY_MISMATCH("Transaction currency mismatch", HTTP_BAD_REQUEST), 
    TRANSACTION_INSUFFICIENT_FUNDS("Insufficient funds for this transaction", HTTP_UNPROCESSABLE_ENTITY), 
    TRANSACTION_INVALID_AMOUNT("Invalid transaction amount", HTTP_BAD_REQUEST), 
    TRANSACTION_NOT_FOUND("Transaction not found", HTTP_NOT_FOUND), 
    TRANSACTION_CURRENCY_INVALID("Invalid transaction currency", HTTP_BAD_REQUEST), 
    TRANSACTION_INVALID_TYPE("Invalid transaction type", HTTP_BAD_REQUEST), 
    TRANSACTION_INVALID_REFERENCE("Invalid transaction reference", HTTP_BAD_REQUEST), 
    ACCOUNT_TYPE_INVALID("Invalid account type", HTTP_BAD_REQUEST), 
    USER_INVALID_ACCESS ("You do not have access to this user", HTTP_FORBIDDEN), 
    USER_DOES_NOT_EXIST("User does not exist", HTTP_NOT_FOUND), 
    AUTH_ACCOUNT_NOT_ACCESSIBLE("You do not have access to this account", HTTP_FORBIDDEN);

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

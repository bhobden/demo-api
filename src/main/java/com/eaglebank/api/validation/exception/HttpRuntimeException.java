package com.eaglebank.api.validation.exception;

public class HttpRuntimeException extends RuntimeException {

    private final int statusCode;

    public HttpRuntimeException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpRuntimeException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

}

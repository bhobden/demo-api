package com.eaglebank.api.validation.exception;

public class CommonException extends HttpRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a CommonException with the specified message and status code.
     *
     * @param message the detail message
     * @param statusCode the HTTP status code
     */
    public CommonException(CommonExceptionType type) {
        super(type.getMessage(), type.getStatusCode());
    }

    /**
     * Constructs a CommonException with the specified message, status code, and cause.
     *
     * @param message the detail messageß
     * @param statusCode the HTTP status code
     * @param cause the cause of the exception
     */
    public CommonException(CommonExceptionType type, Throwable cause) {
        super(type.getMessage(), type.getStatusCode(), cause);
    }
}

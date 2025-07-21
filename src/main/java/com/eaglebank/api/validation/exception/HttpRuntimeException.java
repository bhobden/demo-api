package com.eaglebank.api.validation.exception;

/**
 * Runtime exception that includes an HTTP status code.
 * <p>
 * This exception is intended to be thrown when an error occurs that should be
 * communicated to the client with a specific HTTP status code. It is commonly
 * used as a base class for more specific exceptions (such as validation or authentication errors)
 * that need to propagate an HTTP status code and message.
 * </p>
 */
public class HttpRuntimeException extends RuntimeException {

    /**
     * The HTTP status code associated with this exception.
     */
    private final int statusCode;

    /**
     * Constructs a new HttpRuntimeException with the specified detail message and status code.
     *
     * @param message    the detail message
     * @param statusCode the HTTP status code to return to the client
     */
    public HttpRuntimeException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * Constructs a new HttpRuntimeException with the specified detail message, status code, and cause.
     *
     * @param message    the detail message
     * @param statusCode the HTTP status code to return to the client
     * @param cause      the cause of the exception
     */
    public HttpRuntimeException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    /**
     * Returns the HTTP status code associated with this exception.
     *
     * @return the HTTP status code
     */
    public int getStatusCode() {
        return statusCode;
    }

}

package com.eaglebank.api.validation.exception;

/**
 * Exception thrown for common application errors, such as authentication or server errors.
 * <p>
 * Wraps a {@link CommonExceptionType} to provide a standardized message and HTTP status code.
 * This exception should be thrown for general errors that are not specific to validation logic,
 * such as JWT errors or unknown server errors.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 *   throw new CommonException(CommonExceptionType.INVALID_JWT);
 * </pre>
 */
public class CommonException extends HttpRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a CommonException with the specified exception type.
     *
     * @param type the {@link CommonExceptionType} describing the error and HTTP status code
     */
    public CommonException(CommonExceptionType type) {
        super(type.getMessage(), type.getStatusCode());
    }

    /**
     * Constructs a CommonException with the specified exception type and cause.
     *
     * @param type  the {@link CommonExceptionType} describing the error and HTTP status code
     * @param cause the cause of the exception
     */
    public CommonException(CommonExceptionType type, Throwable cause) {
        super(type.getMessage(), type.getStatusCode(), cause);
    }
}

package com.eaglebank.api.validation.exception;

/**
 * Exception thrown when a validation check fails.
 * <p>
 * Wraps a {@link ValidationExceptionType} to provide a standardized message and status code
 * for validation errors. This exception should be thrown by validation logic when user input
 * or business rules are violated.
 * </p>
 */
public class ValidationException extends HttpRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * The type of validation exception, containing error message and status code.
     */
    private final ValidationExceptionType type;

    /**
     * Constructs a ValidationException with the specified type.
     *
     * @param type the type of validation exception
     */
    public ValidationException(ValidationExceptionType type) {
        super(type.getMessage(), type.getStatusCode());
        this.type = type;
    }

    /**
     * Constructs a ValidationException with the specified type and cause.
     *
     * @param type  the type of validation exception
     * @param cause the cause of the exception
     */
    public ValidationException(ValidationExceptionType type, Throwable cause) {
        super(type.getMessage(), type.getStatusCode(), cause);
        this.type = type;
    }

    /**
     * Returns the {@link ValidationExceptionType} associated with this exception.
     *
     * @return the validation exception type
     */
    public ValidationExceptionType getType() {
        return this.type;
    }
}

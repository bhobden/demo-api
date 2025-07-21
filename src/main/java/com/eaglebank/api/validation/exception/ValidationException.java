package com.eaglebank.api.validation.exception;

public class ValidationException extends HttpRuntimeException {

    private static final long serialVersionUID = 1L;
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

    public ValidationExceptionType getType() {
        return this.type;
   }

}

package com.eaglebank.api.validation;

import com.eaglebank.api.validation.exception.HttpRuntimeException;
import com.eaglebank.api.validation.exception.ValidationException;
import com.eaglebank.api.validation.exception.ValidationExceptionType;

/**
 * Abstract base class for validation logic.
 * <p>
 * Provides a utility method to throw a standardized {@link HttpRuntimeException}
 * when a validation check fails. Subclasses should use the {@code invalid} method
 * to signal validation errors with a specific {@link ValidationExceptionType}.
 * </p>
 */
public abstract class AbstractValidation {

    /**
     * Throws a {@link ValidationException} with the message and status code
     * from the provided {@link ValidationExceptionType}.
     *
     * @param reason The reason for validation failure.
     * @throws ValidationException always thrown to indicate validation failure.
     */
    protected void invalid(ValidationExceptionType reason) {
        throw new ValidationException(reason);
    }
}

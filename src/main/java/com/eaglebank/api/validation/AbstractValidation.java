package com.eaglebank.api.validation;

import com.eaglebank.api.validation.exception.HttpRuntimeException;
import com.eaglebank.api.validation.exception.ValidationExceptionType;

public abstract class AbstractValidation {
    protected void invalid(ValidationExceptionType reason) {
        throw new HttpRuntimeException(reason.getMessage(), reason.getStatusCode());
    }
}

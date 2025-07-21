package com.eaglebank.api.validation;

import org.springframework.stereotype.Component;

import com.eaglebank.api.validation.exception.ValidationExceptionType;

@Component
public class AccountValidation extends AbstractValidation {
    public void validateAccount(String accountId) {
        if (accountId == null || accountId.isEmpty()) {
            invalid(ValidationExceptionType.AUTH_ACCOUNT_NOT_FOUND);
        }
        // Additional validation logic can be added here
    }


}

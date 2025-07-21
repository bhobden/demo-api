package com.eaglebank.api.validation;

import org.springframework.stereotype.Component;

import com.eaglebank.api.model.BankAccountEntity;
import com.eaglebank.api.security.AuthUtils;
import com.eaglebank.api.validation.exception.ValidationExceptionType;

import io.micrometer.common.util.StringUtils;

@Component
public class AccountValidation extends AbstractValidation {
    public void validateAccount(String accountId) {
        if (accountId == null || accountId.isEmpty()) {
            invalid(ValidationExceptionType.AUTH_ACCOUNT_NOT_FOUND);
        }
        // Additional validation logic can be added here
    }

    public void validateRequesterCanAccessAccount(BankAccountEntity account) {
        validateAccountAccessibleByUser(AuthUtils.getUsername(), account);
    }

    public void validateAccountAccessibleByUser(String username, BankAccountEntity account) {
        if (StringUtils.isBlank(username) || account == null || !username.equals(account.getOwnerUsername())) {
            invalid(ValidationExceptionType.AUTH_ACCOUNT_NOT_FOUND);
        }
    }
}

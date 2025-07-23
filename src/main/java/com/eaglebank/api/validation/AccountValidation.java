package com.eaglebank.api.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.eaglebank.api.model.dto.request.CreateBankAccountRequest;
import com.eaglebank.api.model.entity.bankaccount.AccountType;
import com.eaglebank.api.model.entity.bankaccount.BankAccountEntity;
import com.eaglebank.api.security.AuthUtils;
import com.eaglebank.api.validation.exception.ValidationExceptionType;

/**
 * Provides validation logic for bank account-related operations.
 * <p>
 * Ensures that account IDs are valid, and that the authenticated user is
 * authorized to access or modify the specified bank account.
 * </p>
 */
@Component
public class AccountValidation extends AbstractValidation {

    /**
     * Validates that the account ID is not null or empty.
     *
     * @param accountId The account ID to validate.
     * @throws ValidationException if the account ID is null or empty.
     */
    public void validateAccount(String accountId) {
        if (accountId == null || accountId.isEmpty()) {
            invalid(ValidationExceptionType.AUTH_ACCOUNT_NOT_FOUND);
        }
        // Additional validation logic can be added here
    }

    /**
     * Validates that the currently authenticated user can access the given account.
     * Uses the username from the authentication context.
     *
     * @param account The BankAccountEntity to check access for.
     * @throws ValidationException if the user is not authorized to access the account.
     */
    public void validateRequesterCanAccessAccount(BankAccountEntity account) {
        validateAccountAccessibleByUser(AuthUtils.getUsername(), account);
    }

    /**
     * Validates that the specified username can access the given account.
     * Checks that the username is not blank, the account is not null, and the
     * username matches the account owner.
     *
     * @param username The username to check.
     * @param account  The BankAccountEntity to check access for.
     * @throws ValidationException if the user is not authorized to access the account.
     */
    public void validateAccountAccessibleByUser(String username, BankAccountEntity account) {
        if (StringUtils.isBlank(username) || account == null || !username.equals(account.getOwnerUsername())) {
            invalid(ValidationExceptionType.AUTH_ACCOUNT_NOT_FOUND);
        }
    }

    /**
     * Validates that the currently authenticated user can access the given account.
     * Shortcut for validateAccountAccessibleByUser(AuthUtils.getUsername(), account).
     *
     * @param account The BankAccountEntity to check access for.
     * @throws ValidationException if the user is not authorized to access the account.
     */
    public void validateAccountAccessibleByRequestor(BankAccountEntity account) {
        validateAccountAccessibleByUser(AuthUtils.getUsername(), account);
    }

    /**
     * Validates that the account name is not blank.
     *
     * @param accountName The account name to validate.
     * @throws ValidationException if the account name is blank or null.
     */
    public void validateAccountName(String accountName) {
        if (StringUtils.isBlank(accountName)) {
            invalid(ValidationExceptionType.ACCOUNT_NAME_INVALID);
        }
    }

    /**
     * Validates all required fields for creating a new account.
     *
     * @param request The CreateBankAccountRequest containing account details.
     * @throws ValidationException if any required field is invalid.
     */
    public void validateNewAccount(CreateBankAccountRequest request) {
        validateAccountType(request.getAccountType());
        validateAccountName(request.getAccountName());
    }

    /**
     * Validates that the account type is specified.
     *
     * @param accountType The account type to validate.
     * @throws ValidationException if the account type is null.
     */
    public void validateAccountType(AccountType accountType) {
        if (accountType == null) {
            invalid(ValidationExceptionType.ACCOUNT_TYPE_INVALID);
        }
    }
}

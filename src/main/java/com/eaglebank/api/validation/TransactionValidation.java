package com.eaglebank.api.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.eaglebank.api.model.dto.request.CreateTransactionRequest;
import com.eaglebank.api.model.entity.bankaccount.BankAccountEntity;
import com.eaglebank.api.model.entity.bankaccount.transaction.TransactionEntity;
import com.eaglebank.api.validation.exception.ValidationExceptionType;

/**
 * Provides validation logic for transaction-related operations.
 * <p>
 * Ensures that transaction requests and entities conform to business rules
 * such as amount limits, currency compatibility, sufficient funds, and required fields.
 * </p>
 */
@Component
public class TransactionValidation extends AbstractValidation {

    /**
     * Validates that a withdrawal transaction is allowed for the given account.
     * Checks currency compatibility, amount validity, and sufficient funds.
     *
     * @param request The transaction request.
     * @param account The bank account entity.
     * @throws ValidationException if any validation fails.
     */
    public void validateCanWithdraw(CreateTransactionRequest request, BankAccountEntity account) {
        validateCompatibleCurrency(request, account);
        validateTransactionAmount(request);
        validateFundsAvailable(request, account);
    }

    /**
     * Validates that a deposit transaction is allowed for the given account.
     * Checks currency compatibility and amount validity.
     *
     * @param request The transaction request.
     * @param account The bank account entity.
     * @throws ValidationException if any validation fails.
     */
    public void validateCanDeposit(CreateTransactionRequest request, BankAccountEntity account) {
        validateCompatibleCurrency(request, account);
        validateTransactionAmount(request);
    }

    /**
     * Validates that the transaction currency matches the account currency.
     *
     * @param request The transaction request.
     * @param account The bank account entity.
     * @throws ValidationException if currencies do not match.
     */
    public void validateCompatibleCurrency(CreateTransactionRequest request, BankAccountEntity account) {
        if (!request.getCurrency().equals(account.getCurrency())) {
            invalid(ValidationExceptionType.TRANSACTION_CURRENCY_MISMATCH);
        }
    }

    /**
     * Validates that the account has sufficient funds for the transaction.
     *
     * @param request The transaction request.
     * @param account The bank account entity.
     * @throws ValidationException if funds are insufficient.
     */
    public void validateFundsAvailable(CreateTransactionRequest request, BankAccountEntity account) {
        if (request.getAmount() > account.getBalance()) {
            invalid(ValidationExceptionType.TRANSACTION_INSUFFICIENT_FUNDS);
        }
    }

    /**
     * Validates that the transaction amount is within allowed limits.
     *
     * @param request The transaction request.
     * @throws ValidationException if the amount is invalid.
     */
    public void validateTransactionAmount(CreateTransactionRequest request) {
        if (request.getAmount() <= 0 || request.getAmount() > 100000) {
            invalid(ValidationExceptionType.TRANSACTION_INVALID_AMOUNT);
        }
    }

    /**
     * Validates that the transaction belongs to the specified account.
     *
     * @param transaction The transaction entity.
     * @param account The bank account entity.
     * @throws ValidationException if the transaction is not for the account.
     */
    public void validateTransactionIsForAccount(TransactionEntity transaction, BankAccountEntity account) {
        if (!transaction.getAccountNumber().equals(account.getAccountNumber())) {
            invalid(ValidationExceptionType.TRANSACTION_NOT_FOUND);
        }
    }

    /**
     * Validates that the transaction currency is specified.
     *
     * @param request The transaction request.
     * @throws ValidationException if the currency is null.
     */
    public void validateCurrency(CreateTransactionRequest request) {
        if (request.getCurrency() == null) {
            invalid(ValidationExceptionType.TRANSACTION_CURRENCY_INVALID);
        }
    }

    /**
     * Validates that the transaction entity exists (is not null).
     *
     * @param transaction The transaction entity.
     * @throws ValidationException if the transaction is null.
     */
    public void validateTransactionExists(TransactionEntity transaction) {
        if (transaction == null) {
            invalid(ValidationExceptionType.TRANSACTION_NOT_FOUND);
        }
    }

    /**
     * Validates that the transaction request exists (is not null).
     *
     * @param transaction The transaction request.
     * @throws ValidationException if the transaction request is null.
     */
    public void validateTransactionExists(CreateTransactionRequest transaction) {
        if (transaction == null) {
            invalid(ValidationExceptionType.TRANSACTION_NOT_FOUND);
        }
    }

    /**
     * Validates that the transaction type is specified.
     *
     * @param transactionRequest The transaction request.
     * @throws ValidationException if the type is null.
     */
    public void validateTransactionType(CreateTransactionRequest transactionRequest) {
        if (transactionRequest.getType() == null) {
            invalid(ValidationExceptionType.TRANSACTION_INVALID_TYPE);
        }
    }

    /**
     * Validates that the transaction reference is not blank.
     * (Uncomment in validateTransactionRequest if reference is mandatory.)
     *
     * @param transactionRequest The transaction request.
     * @throws ValidationException if the reference is blank.
     */
    public void validateTransactionReference(CreateTransactionRequest transactionRequest) {
        if (StringUtils.isBlank(transactionRequest.getReference())) {
            invalid(ValidationExceptionType.TRANSACTION_INVALID_REFERENCE);
        }
    }

    /**
     * Validates all required fields for a transaction request.
     * Checks existence, currency, type, and amount.
     * Reference validation is optional (uncomment if required).
     *
     * @param transactionRequest The transaction request.
     * @throws ValidationException if any field is invalid.
     */
    public void validateTransactionRequest(CreateTransactionRequest transactionRequest) {
        validateTransactionExists(transactionRequest);
        validateCurrency(transactionRequest);
        validateTransactionType(transactionRequest);
        validateTransactionAmount(transactionRequest);
        //validateTransactionReference(transactionRequest); // Uncomment if reference is mandatory
    }

    public void validateTransactionAccessibleByRequestor(TransactionEntity transaction, BankAccountEntity account) {
        validateTransactionExists(transaction);
        validateTransactionIsForAccount(transaction, account);
    }
}

package com.eaglebank.api.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.eaglebank.api.model.dto.request.CreateTransactionRequest;
import com.eaglebank.api.model.entity.bankaccount.BankAccountEntity;
import com.eaglebank.api.model.entity.bankaccount.transaction.TransactionEntity;
import com.eaglebank.api.validation.exception.ValidationExceptionType;

@Component
public class TransactionValidation extends AbstractValidation {

    public void validateCanWithdraw(CreateTransactionRequest request, BankAccountEntity account) {
        validateCompatibleCurrency(request, account);
        validateTransactionAmount(request);
        validateFundsAvailable(request, account);
    }

    public void validateCanDeposit(CreateTransactionRequest request, BankAccountEntity account) {
        validateCompatibleCurrency(request, account);
        validateTransactionAmount(request);
    }

    public void validateCompatibleCurrency(CreateTransactionRequest request, BankAccountEntity account) {
        if (!request.getCurrency().equals(account.getCurrency())) {
            invalid(ValidationExceptionType.TRANSACTION_CURRENCY_MISMATCH);
        }
    }

    public void validateFundsAvailable(CreateTransactionRequest request, BankAccountEntity account) {
        if (request.getAmount() > account.getBalance()) {
            invalid(ValidationExceptionType.TRANSACTION_INSUFFICIENT_FUNDS);
        }
    }

    public void validateTransactionAmount(CreateTransactionRequest request) {
        if (request.getAmount() <= 0 || request.getAmount() > 100000) {
            invalid(ValidationExceptionType.TRANSACTION_INVALID_AMOUNT);
        }
    }

    public void validateTransactionIsForAccount(TransactionEntity transaction, BankAccountEntity account) {
        if (!transaction.getAccountNumber().equals(account.getAccountNumber())) {
            invalid(ValidationExceptionType.TRANSACTION_NOT_FOUND);
        }
    }

    public void validateCurrency(CreateTransactionRequest request) {
        if (request.getCurrency() == null) {
            invalid(ValidationExceptionType.TRANSACTION_CURRENCY_INVALID);
        }
    }

    public void validateTransactionExists(TransactionEntity transaction) {
        if (transaction == null) {
            invalid(ValidationExceptionType.TRANSACTION_NOT_FOUND);
        }
    }

    public void validateTransactionExists(CreateTransactionRequest transaction) {
        if (transaction == null) {
            invalid(ValidationExceptionType.TRANSACTION_NOT_FOUND);
        }
    }

    public void validateTransactionType(CreateTransactionRequest transactionRequest) {
        if (transactionRequest.getType() == null) {
            invalid(ValidationExceptionType.TRANSACTION_INVALID_TYPE);
        }
    }

    public void validateTransactionReference(CreateTransactionRequest transactionRequest) {
        if (StringUtils.isBlank(transactionRequest.getReference())) {
            invalid(ValidationExceptionType.TRANSACTION_INVALID_REFERENCE);
        }
    }

    public void validateTransactionRequest(CreateTransactionRequest transactionRequest) {
        validateTransactionExists(transactionRequest);
        validateCurrency(transactionRequest);
        validateTransactionType(transactionRequest);
        validateTransactionAmount(transactionRequest);
        //validateTransactionReference(transactionRequest); // Uncomment if reference is mandatory
    }
}

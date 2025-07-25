package com.eaglebank.api.model.entity.account.transaction;

/**
 * Enum for transaction types.
 */
public enum TransactionType {
    DEPOSIT(1),
    WITHDRAWAL(2);

    private final int code;

    TransactionType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
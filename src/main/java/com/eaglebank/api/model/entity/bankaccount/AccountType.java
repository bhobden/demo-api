package com.eaglebank.api.model.entity.bankaccount;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Enum for account types.
 */
public enum AccountType {
    PERSONAL(0),
    SAVINGS(1),
    CREDIT(2);

    public static final List<AccountType> ALL = Collections.unmodifiableList(Arrays.asList(values()));

    private final int value;

    AccountType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
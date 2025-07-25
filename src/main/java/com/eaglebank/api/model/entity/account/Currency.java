package com.eaglebank.api.model.entity.account;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Enum for supported currencies.
 */
public enum Currency {
    USD(0),
    GBP(1);

    public static final List<Currency> ALL = Collections.unmodifiableList(Arrays.asList(values()));

    private final int value;

    Currency(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
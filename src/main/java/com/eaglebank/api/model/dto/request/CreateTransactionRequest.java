package com.eaglebank.api.model.dto.request;

import com.eaglebank.api.model.entity.account.Currency;
import com.eaglebank.api.model.entity.account.transaction.TransactionType;

public class CreateTransactionRequest {
    private double amount;
    private Currency currency;
    private TransactionType type;
    private String reference;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
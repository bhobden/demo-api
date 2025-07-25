package com.eaglebank.api.model.dto.response;

import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.eaglebank.api.model.entity.account.Currency;
import com.eaglebank.api.model.entity.account.transaction.TransactionType;

/**
 * DTO representing a transaction response.
 * <p>
 * Contains details about a transaction, such as amount, currency, type, reference,
 * associated user, and timestamps.
 * </p>
 */
public class TransactionResponse {
    private String id;
    private double amount;
    private Currency currency;
    private TransactionType type;
    private String reference;
    private String userId;
    private LocalDateTime createdTimestamp;

    public String getId() {
        return id;
    }

    public TransactionResponse setId(String id) {
        this.id = id;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionResponse setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public TransactionResponse setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionResponse setType(TransactionType type) {
        this.type = type;
        return this;
    }

    public String getReference() {
        return reference;
    }

    public TransactionResponse setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public TransactionResponse setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public TransactionResponse setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionResponse)) return false;
        TransactionResponse that = (TransactionResponse) o;
        return new EqualsBuilder()
                .append(amount, that.amount)
                .append(id, that.id)
                .append(currency, that.currency)
                .append(type, that.type)
                .append(reference, that.reference)
                .append(userId, that.userId)
                .append(createdTimestamp, that.createdTimestamp)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(amount)
                .append(currency)
                .append(type)
                .append(reference)
                .append(userId)
                .append(createdTimestamp)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("amount", amount)
                .append("currency", currency)
                .append("type", type)
                .append("reference", reference)
                .append("userId", userId)
                .append("createdTimestamp", createdTimestamp)
                .toString();
    }
}
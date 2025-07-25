package com.eaglebank.api.model.entity.account.transaction;

import jakarta.persistence.*;

import java.time.Instant;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.eaglebank.api.model.entity.account.Currency;

/**
 * Entity representing a bank account transaction.
 * <p>
 * Contains details such as transaction ID, amount, currency, type, reference, user ID, account number, and creation timestamp.
 * </p>
 */
@Entity
@Table(name = "transactions")
public class TransactionEntity {

    /** Unique transaction ID (e.g., tan-123abc). */
    @Id
    @Column(length = 32, nullable = false, unique = true)
    private String id;

    /** Transaction amount (0.00 - 10000.00). */
    @Column(nullable = false)
    private double amount;

    /** Transaction currency (e.g., GBP). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private Currency currency;

    /** Transaction type (deposit or withdrawal). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private TransactionType type;

    /** Optional reference or description for the transaction. */
    @Column(length = 128)
    private String reference;

    /** ID of the user associated with this transaction (e.g., usr-abc123). */
    @Column(length = 32, nullable = false)
    private String userId;

    /** Account number of the bank account associated with this transaction. */
    @Column(length = 32, nullable = false)
    private String accountNumber;

    /** Timestamp when the transaction was created. */
    @Column(nullable = false)
    private Instant createdTimestamp;

    public String getId() {
        return id;
    }

    public TransactionEntity setId(String id) {
        this.id = id;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionEntity setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public TransactionEntity setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionEntity setType(TransactionType type) {
        this.type = type;
        return this;
    }

    public String getReference() {
        return reference;
    }

    public TransactionEntity setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public TransactionEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public TransactionEntity setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public Instant getCreatedTimestamp() {
        return createdTimestamp;
    }

    public TransactionEntity setCreatedTimestamp(Instant createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionEntity)) return false;
        TransactionEntity that = (TransactionEntity) o;
        return new EqualsBuilder()
                .append(amount, that.amount)
                .append(id, that.id)
                .append(currency, that.currency)
                .append(type, that.type)
                .append(reference, that.reference)
                .append(userId, that.userId)
                .append(accountNumber, that.accountNumber)
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
                .append(accountNumber)
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
                .append("accountNumber", accountNumber)
                .append("createdTimestamp", createdTimestamp)
                .toString();
    }
}

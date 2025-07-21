package com.eaglebank.api.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Entity representing a bank account.
 * Covers all required fields: accountNumber, sortCode, name, accountType,
 * balance, currency,
 * createdTimestamp, updatedTimestamp.
 */
@Entity
public class BankAccountEntity {

    @Id
    @Column(nullable = false, unique = true, length = 16)
    private String accountNumber;

    @Column(nullable = false, length = 8)
    private String sortCode;

    @Column(nullable = false, length = 128)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private AccountType accountType;

    @Column(nullable = false)
    private Double balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 3)
    private Currency currency;

    @Column(nullable = false)
    private Instant createdTimestamp;

    @Column(nullable = false)
    private Instant updatedTimestamp;

    @Column(nullable = false, length = 128)
    private String ownerUsername;

    public String getAccountNumber() {
        return accountNumber;
    }

    public BankAccountEntity setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public String getSortCode() {
        return sortCode;
    }

    public BankAccountEntity setSortCode(String sortCode) {
        this.sortCode = sortCode;
        return this;
    }

    public String getAccountName() {
        return name;
    }

    public BankAccountEntity setAccountName(String name) {
        this.name = name;
        return this;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public BankAccountEntity setAccountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public Double getBalance() {
        return balance;
    }

    public BankAccountEntity setBalance(Double balance) {
        this.balance = balance;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BankAccountEntity setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public BankAccountEntity setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
        return this;
    }

    public Instant getCreatedTimestamp() {
        return createdTimestamp;
    }

    public BankAccountEntity setCreatedTimestamp(Instant createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
        return this;
    }

    public Instant getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public BankAccountEntity setUpdatedTimestamp(Instant updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        BankAccountEntity that = (BankAccountEntity) o;

        return new EqualsBuilder()
                .append(accountNumber, that.accountNumber)
                .append(sortCode, that.sortCode)
                .append(name, that.name)
                .append(accountType, that.accountType)
                .append(balance, that.balance)
                .append(currency, that.currency)
                .append(createdTimestamp, that.createdTimestamp)
                .append(updatedTimestamp, that.updatedTimestamp)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(accountNumber)
                .append(sortCode)
                .append(name)
                .append(accountType)
                .append(balance)
                .append(currency)
                .append(createdTimestamp)
                .append(updatedTimestamp)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("accountNumber", accountNumber)
                .append("sortCode", sortCode)
                .append("name", name)
                .append("accountType", accountType)
                .append("balance", balance)
                .append("currency", currency)
                .append("createdTimestamp", createdTimestamp)
                .append("updatedTimestamp", updatedTimestamp)
                .toString();
    }

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
}

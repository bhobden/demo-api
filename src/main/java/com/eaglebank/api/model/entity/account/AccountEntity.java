package com.eaglebank.api.model.entity.account;

import jakarta.persistence.*;
import java.time.Instant;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Entity representing a bank account.
 * <p>
 * Covers all required fields: accountNumber, sortCode, name, accountType,
 * balance, currency, createdTimestamp, updatedTimestamp, and ownerUsername.
 * </p>
 */
@Entity
public class AccountEntity {

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

    public AccountEntity setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public String getSortCode() {
        return sortCode;
    }

    public AccountEntity setSortCode(String sortCode) {
        this.sortCode = sortCode;
        return this;
    }

    public String getAccountName() {
        return name;
    }

    public AccountEntity setAccountName(String name) {
        this.name = name;
        return this;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public AccountEntity setAccountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public Double getBalance() {
        return balance;
    }

    public AccountEntity setBalance(Double balance) {
        this.balance = balance;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public AccountEntity setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public AccountEntity setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
        return this;
    }

    public Instant getCreatedTimestamp() {
        return createdTimestamp;
    }

    public AccountEntity setCreatedTimestamp(Instant createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
        return this;
    }

    public Instant getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public AccountEntity setUpdatedTimestamp(Instant updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        AccountEntity that = (AccountEntity) o;

        return new EqualsBuilder()
                .append(accountNumber, that.accountNumber)
                .append(sortCode, that.sortCode)
                .append(name, that.name)
                .append(accountType, that.accountType)
                .append(balance, that.balance)
                .append(currency, that.currency)
                .append(createdTimestamp, that.createdTimestamp)
                .append(updatedTimestamp, that.updatedTimestamp)
                .append(ownerUsername, that.ownerUsername)
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
                .append(ownerUsername)
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
                .append("ownerUsername", ownerUsername)
                .toString();
    }
}

package com.eaglebank.api.model.dto.response;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.eaglebank.api.model.entity.account.AccountType;
import com.eaglebank.api.model.entity.account.Currency;

/**
 * DTO representing a bank account response.
 * <p>
 * Contains details about a bank account, such as account number, sort code, name,
 * type, balance, currency, and timestamps.
 * </p>
 */
public class BankAccountResponse {
    private String accountNumber;
    private String sortCode;
    private String name;
    private AccountType accountType;
    private double balance;
    private Currency currency;
    private LocalDateTime createdTimestamp;
    private LocalDateTime updatedTimestamp;

    public String getAccountNumber() {
        return accountNumber;
    }

    public BankAccountResponse setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public String getSortCode() {
        return sortCode;
    }

    public BankAccountResponse setSortCode(String sortCode) {
        this.sortCode = sortCode;
        return this;
    }

    public String getAccountName() {
        return name;
    }

    public BankAccountResponse setAccountName(String name) {
        this.name = name;
        return this;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public BankAccountResponse setAccountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public BankAccountResponse setBalance(double balance) {
        this.balance = balance;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BankAccountResponse  setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public BankAccountResponse setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
        return this;
    }

    public LocalDateTime getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public BankAccountResponse setUpdatedTimestamp(LocalDateTime updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccountResponse)) return false;
        BankAccountResponse that = (BankAccountResponse) o;
        return new EqualsBuilder()
                .append(balance, that.balance)
                .append(accountNumber, that.accountNumber)
                .append(sortCode, that.sortCode)
                .append(name, that.name)
                .append(accountType, that.accountType)
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
}
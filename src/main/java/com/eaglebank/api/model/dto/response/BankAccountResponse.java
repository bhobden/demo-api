package com.eaglebank.api.model.dto.response;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.eaglebank.api.model.entity.bankaccount.AccountType;
import com.eaglebank.api.model.entity.bankaccount.Currency;

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

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getAccountName() {
        return name;
    }

    public void setAccountName(String name) {
        this.name = name;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public LocalDateTime getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(LocalDateTime updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
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
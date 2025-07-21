package com.eaglebank.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false)
    private Double balance;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String ownerUsername;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        BankAccount that = (BankAccount) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(accountName, that.accountName)
                .append(accountType, that.accountType)
                .append(balance, that.balance)
                .append(createdAt, that.createdAt)
                .append(ownerUsername, that.ownerUsername)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(accountName)
                .append(accountType)
                .append(balance)
                .append(createdAt)
                .append(ownerUsername)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("accountName", accountName)
                .append("accountType", accountType)
                .append("balance", balance)
                .append("createdAt", createdAt)
                .append("ownerUsername", ownerUsername)
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
}

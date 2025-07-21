package com.eaglebank.api.model.dto.request;

import com.eaglebank.api.model.BankAccount.AccountType;

public class CreateBankAccountRequest {
    private String name;
    private AccountType accountType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

}
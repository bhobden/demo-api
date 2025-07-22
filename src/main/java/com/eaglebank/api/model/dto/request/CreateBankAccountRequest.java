package com.eaglebank.api.model.dto.request;

import com.eaglebank.api.model.entity.bankaccount.AccountType;

public class CreateBankAccountRequest {
    private String name;
    private AccountType accountType;

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

}
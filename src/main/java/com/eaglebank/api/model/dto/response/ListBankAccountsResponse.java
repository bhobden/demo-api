package com.eaglebank.api.model.dto.response;

import java.util.List;

public class ListBankAccountsResponse {
    private List<BankAccountResponse> accounts;

    public List<BankAccountResponse> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccountResponse> accounts) {
        this.accounts = accounts;
    }
}
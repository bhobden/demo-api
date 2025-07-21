package com.eaglebank.api.model.dto.response;

import java.util.List;

public class ListTransactionsResponse {
    private List<TransactionResponse> transactions;

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionResponse> transactions) {
        this.transactions = transactions;
    }
}
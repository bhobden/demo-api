package com.eaglebank.api.controller.account.transactions;

import com.eaglebank.api.controller.ControllerConstants;
import com.eaglebank.api.model.dto.request.CreateTransactionRequest;
import com.eaglebank.api.model.dto.response.ListTransactionsResponse;
import com.eaglebank.api.model.dto.response.TransactionResponse;
import com.eaglebank.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for fetching lists of transactions for accounts or users.
 */
@RestController
@RequestMapping(ControllerConstants.TRANSACTIONS_PATH)
public class TransactionsController {

    @Autowired
    private TransactionService transactionService;

    /**
     * Fetch all transactions for a given account.
     */
    @GetMapping
    public ResponseEntity<ListTransactionsResponse> getTransactionsForAccount(
            @PathVariable("accountId") String accountNumber) {
        ListTransactionsResponse response = transactionService.getTransactions(accountNumber);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @PathVariable("accountId") String accountNumber,
            @RequestBody CreateTransactionRequest transactionRequest) {
        TransactionResponse response = transactionService.createTransaction(accountNumber, transactionRequest);
        return ResponseEntity.ok(response);
    }
}

package com.eaglebank.api.controller.account.transactions;

import com.eaglebank.api.controller.ControllerConstants;
import com.eaglebank.api.model.dto.response.TransactionResponse;
import com.eaglebank.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for fetching, updating, or deleting an individual transaction.
 */
@RestController
@RequestMapping(ControllerConstants.TRANSACTIONS_WITHID_PATH)
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * Fetch a single transaction by its ID.
     */
    @GetMapping
    public ResponseEntity<TransactionResponse> getTransaction(
            @PathVariable String accountNumber,
            @PathVariable String transactionId) {
        TransactionResponse response = transactionService.getTransaction(accountNumber, transactionId);
        return ResponseEntity.ok(response);
    }
}

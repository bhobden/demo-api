package com.eaglebank.api.controller;

import com.eaglebank.api.model.dto.request.CreateBankAccountRequest;
import com.eaglebank.api.model.dto.response.BankAccountResponse;
import com.eaglebank.api.model.dto.response.ListBankAccountsResponse;
import com.eaglebank.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerConstants.ACCOUNTS_PATH)
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<BankAccountResponse> createAccount(
            @RequestBody CreateBankAccountRequest request) {
        BankAccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<ListBankAccountsResponse> listAccounts() {
        ListBankAccountsResponse response = accountService.listAccounts();
        return ResponseEntity.ok(response);
    }
}

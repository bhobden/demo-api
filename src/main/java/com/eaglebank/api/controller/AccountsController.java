package com.eaglebank.api.controller;

import com.eaglebank.api.model.dto.request.CreateBankAccountRequest;
import com.eaglebank.api.model.dto.response.BankAccountResponse;
import com.eaglebank.api.model.dto.response.ListBankAccountsResponse;
import com.eaglebank.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<BankAccountResponse> createAccount(
            @RequestBody CreateBankAccountRequest request,
            Authentication authentication) {

        String username = authentication.getName();
        BankAccountResponse response = accountService.createAccount(request, username);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<ListBankAccountsResponse> listAccounts(Authentication authentication) {
        ListBankAccountsResponse response = accountService.listAccounts(authentication);
        return ResponseEntity.ok(response);
    }
}

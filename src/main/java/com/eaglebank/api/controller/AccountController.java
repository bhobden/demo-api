package com.eaglebank.api.controller;

import com.eaglebank.api.model.dto.request.UpdateBankAccountRequest;
import com.eaglebank.api.model.dto.response.BankAccountResponse;
import com.eaglebank.api.service.AccountService;
import jakarta.validation.constraints.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts/{accountNumber}")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<BankAccountResponse> fetchAccount(
            @PathVariable @Pattern(regexp = "^01\\d{6}$") String accountNumber,
            Authentication authentication) {
        BankAccountResponse response = accountService.fetchAccountByAccountNumber(authentication, accountNumber);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<BankAccountResponse> updateAccount(
            @PathVariable @Pattern(regexp = "^01\\d{6}$") String accountNumber,
            @RequestBody UpdateBankAccountRequest updateRequest,
            Authentication authentication) {
        BankAccountResponse response = accountService.updateAccountByAccountNumber(authentication, accountNumber,
                updateRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<Void> deleteAccount(
            @PathVariable @Pattern(regexp = "^01\\d{6}$") String accountNumber,
            Authentication authentication) {
        accountService.deleteAccountByAccountNumber(authentication, accountNumber);
        return ResponseEntity.noContent().build();
    }
}

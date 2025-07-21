package com.eaglebank.api.controller;

import com.eaglebank.api.model.dto.request.UpdateBankAccountRequest;
import com.eaglebank.api.model.dto.response.BankAccountResponse;
import com.eaglebank.api.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerConstants.ACCOUNTS_WITHID_PATH)
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<BankAccountResponse> fetchAccount(
            @PathVariable("accountId") String accountId) {
        BankAccountResponse response = accountService.getAccount(accountId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<BankAccountResponse> updateAccount(
            @PathVariable("accountId") String accountId,
            @RequestBody UpdateBankAccountRequest updateRequest) {
        BankAccountResponse response = accountService.updateAccount(accountId,
                updateRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<Void> deleteAccount(
            @PathVariable("accountId") String accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}

package com.eaglebank.api.controller.account;

import com.eaglebank.api.controller.ControllerConstants;
import com.eaglebank.api.model.dto.request.UpdateBankAccountRequest;
import com.eaglebank.api.model.dto.response.BankAccountResponse;
import com.eaglebank.api.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing individual bank accounts.
 * <p>
 * Provides endpoints to fetch, update, and delete a specific bank account by its ID.
 * All endpoints require authentication and are secured by the application's security configuration.
 * </p>
 *
 * <h3>Endpoints:</h3>
 * <ul>
 *   <li>GET    /api/accounts/{accountId}    - Fetch account details</li>
 *   <li>PATCH  /api/accounts/{accountId}    - Update account details</li>
 *   <li>DELETE /api/accounts/{accountId}    - Delete the account</li>
 * </ul>
 */
@RestController
@RequestMapping(ControllerConstants.ACCOUNTS_WITHID_PATH)
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * Fetches the details of a specific bank account.
     *
     * @param accountId the unique identifier of the account
     * @return ResponseEntity containing the account details
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<BankAccountResponse> fetchAccount(
            @PathVariable("accountId") String accountId) {
        BankAccountResponse response = accountService.getAccount(accountId);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates the details of a specific bank account.
     *
     * @param accountId     the unique identifier of the account
     * @param updateRequest the request body containing updated account fields
     * @return ResponseEntity containing the updated account details
     */
    @PatchMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<BankAccountResponse> updateAccount(
            @PathVariable("accountId") String accountId,
            @RequestBody UpdateBankAccountRequest updateRequest) {
        BankAccountResponse response = accountService.updateAccount(accountId, updateRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a specific bank account.
     *
     * @param accountId the unique identifier of the account
     * @return ResponseEntity with HTTP 204 No Content if deletion is successful
     */
    @DeleteMapping(produces = "application/json")
    public ResponseEntity<Void> deleteAccount(
            @PathVariable("accountId") String accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}

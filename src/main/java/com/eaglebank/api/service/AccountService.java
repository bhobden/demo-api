package com.eaglebank.api.service;

import com.eaglebank.api.model.BankAccount;
import com.eaglebank.api.model.dto.request.CreateBankAccountRequest;
import com.eaglebank.api.model.dto.request.UpdateBankAccountRequest;
import com.eaglebank.api.model.dto.response.BankAccountResponse;
import com.eaglebank.api.model.dto.response.ListBankAccountsResponse;
import com.eaglebank.api.repository.BankAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService extends AbstractService {

    @Autowired
    private BankAccountRepository accountRepository;

    /**
     * Creates a new bank account for the authenticated user.
     */
    public BankAccountResponse createAccount(CreateBankAccountRequest request, String ownerUsername) {
        BankAccount account = new BankAccount();
        account.setAccountName(request.getAccountName());
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getInitialDeposit());
        account.setOwnerUsername(ownerUsername);
        account = accountRepository.save(account);

        BankAccountResponse response = new BankAccountResponse();
        response.setId(account.getId());
        response.setAccountName(account.getAccountName());
        response.setAccountType(account.getAccountType());
        response.setBalance(account.getBalance());
        response.setCreatedAt(account.getCreatedAt());

        return response;
    }

    public ListBankAccountsResponse listAccounts(Authentication authentication) {
        List<BankAccount> accounts = accountRepository.findByOwnerUsername(authentication.getName());
        List<BankAccountResponse> responseList = accounts.stream().map(account -> {
            BankAccountResponse r = new BankAccountResponse();
            r.setId(account.getId());
            r.setAccountName(account.getAccountName());
            r.setAccountType(account.getAccountType());
            r.setBalance(account.getBalance());
            r.setCreatedAt(account.getCreatedAt());
            return r;
        }).toList();

        return new ListBankAccountsResponse(responseList);
    }

    public void deleteAccountByAccountNumber(Authentication authentication,
            String accountNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAccountByAccountNumber'");
    }

    public BankAccountResponse fetchAccountByAccountNumber(Authentication authentication,
            String accountNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchAccountByAccountNumber'");
    }

    public BankAccountResponse updateAccountByAccountNumber(Authentication authentication,
            String accountNumber, UpdateBankAccountRequest updateRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAccountByAccountNumber'");
    }
}

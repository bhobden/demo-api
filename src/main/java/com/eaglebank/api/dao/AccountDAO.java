package com.eaglebank.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eaglebank.api.model.BankAccountEntity;
import com.eaglebank.api.repository.BankAccountRepository;

@Repository
public class AccountDAO {

    @Autowired
    private BankAccountRepository accountRepository;

    public BankAccountEntity getAccount(String accountId) {
        return accountRepository.findById(accountId)
                .orElse(null);
    }

    public List<BankAccountEntity> getUsersAccount(String ownerUsername) {
        return accountRepository.findByOwnerUsername(ownerUsername);
    }

    public BankAccountEntity createBankAccount(BankAccountEntity account) {
        return accountRepository.save(account);
    }

    public void deleteBankAccount(BankAccountEntity account) {
        accountRepository.delete(account);
    }

    public BankAccountEntity updateBankAccount(BankAccountEntity account) {
        return accountRepository.save(account);
    }
}

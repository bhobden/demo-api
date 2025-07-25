package com.eaglebank.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eaglebank.api.model.entity.account.AccountEntity;
import com.eaglebank.api.repository.BankAccountRepository;

/**
 * Data Access Object (DAO) for bank account entities.
 * Provides methods to interact with the BankAccountRepository for CRUD operations.
 *
 * Usage example:
 * <pre>
 * {@code
 * @Autowired
 * private AccountDAO accountDAO;
 *
 * // Create a new account
 * BankAccountEntity account = new BankAccountEntity();
 * accountDAO.createBankAccount(account);
 *
 * // Retrieve an account by ID
 * BankAccountEntity found = accountDAO.getAccount(accountId);
 *
 * // List all accounts for a user
 * List<BankAccountEntity> accounts = accountDAO.getUsersAccount(ownerUsername);
 *
 * // Update an account
 * account.setBalance(100.0);
 * accountDAO.updateBankAccount(account);
 *
 * // Delete an account
 * accountDAO.deleteBankAccount(account);
 * }
 * </pre>
 */
@Repository
public class AccountDAO {

    @Autowired
    private BankAccountRepository accountRepository;

    /**
     * Retrieves a bank account entity by its ID.
     *
     * @param accountId The ID of the account to retrieve.
     * @return The BankAccountEntity if found, otherwise null.
     */
    public AccountEntity getAccount(String accountId) {
        return accountRepository.findById(accountId)
                .orElse(null);
    }

    /**
     * Retrieves all bank accounts for a given owner username.
     *
     * @param ownerUsername The username of the account owner.
     * @return List of BankAccountEntity belonging to the user.
     */
    public List<AccountEntity> getUsersAccount(String ownerUsername) {
        return accountRepository.findByOwnerUsername(ownerUsername);
    }

    /**
     * Persists a new bank account entity in the repository.
     *
     * @param account The BankAccountEntity to create.
     * @return The saved BankAccountEntity.
     */
    public AccountEntity createBankAccount(AccountEntity account) {
        return accountRepository.save(account);
    }

    /**
     * Deletes a bank account entity from the repository.
     *
     * @param account The BankAccountEntity to delete.
     */
    public void deleteBankAccount(AccountEntity account) {
        accountRepository.delete(account);
    }

    /**
     * Updates an existing bank account entity in the repository.
     *
     * @param account The BankAccountEntity to update.
     * @return The updated BankAccountEntity.
     */
    public AccountEntity updateBankAccount(AccountEntity account) {
        return accountRepository.save(account);
    }
}

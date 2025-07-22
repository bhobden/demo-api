package com.eaglebank.api.dao;

import com.eaglebank.api.model.entity.bankaccount.transaction.TransactionEntity;
import com.eaglebank.api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Data Access Object (DAO) for transaction entities.
 * <p>
 * Provides methods to interact with the TransactionRepository for CRUD
 * operations
 * and custom queries related to transactions.
 * </p>
 */
@Component
public class TransactionDAO {

    @Autowired
    protected TransactionRepository transactionRepository;

    /**
     * Saves a transaction entity to the database.
     *
     * @param transaction the transaction entity to save
     * @return the saved transaction entity
     */
    public TransactionEntity createTransaction(TransactionEntity transaction) {
        return transactionRepository.save(transaction);
    }

    /**
     * Finds a transaction by its ID.
     *
     * @param id the transaction ID
     * @return the transaction if found, or null if not found
     */
    public TransactionEntity findTransactionById(String id) {
        return transactionRepository.findById(id).orElse(null);
    }

    /**
     * Finds all transactions associated with a specific user ID.
     *
     * @param userId the user ID
     * @return a list of transactions for the specified user
     */
    public List<TransactionEntity> findTransactionsByUserId(String userId) {
        return transactionRepository.findByUserId(userId);
    }

    /**
     * Finds all transactions associated with a specific bank account ID.
     *
     * @param bankAccountId the bank account ID
     * @return a list of transactions for the specified bank account
     */
    public List<TransactionEntity> findTransactionsByAccountId(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }

    /**
     * Deletes a transaction by its ID.
     *
     * @param id the transaction ID
     */
    public void deleteTransaction(String id) {
        transactionRepository.deleteById(id);
    }
}

package com.eaglebank.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eaglebank.api.model.entity.account.transaction.TransactionEntity;

/**
 * Repository interface for accessing and managing {@link TransactionEntity} data.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations and custom queries
 * for transactions.
 * </p>
 */
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    /**
     * Finds all transactions for a given user ID.
     *
     * @param userId the user ID to search for
     * @return a list of {@link TransactionEntity} objects for the user
     */
    List<TransactionEntity> findByUserId(String userId);

    /**
     * Finds all transactions for a given account number.
     *
     * @param accountNumber the account number to search for
     * @return a list of {@link TransactionEntity} objects for the account
     */
    List<TransactionEntity> findByAccountNumber(String accountNumber);
}

package com.eaglebank.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eaglebank.api.model.entity.bankaccount.transaction.TransactionEntity;

/**
 * JPA repository interface to access User entities.
 * Spring Data JPA automatically implements the method based on signature.
 */
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
    List<TransactionEntity> findByUserId(String userId);
    List<TransactionEntity> findByAccountNumber(String accountNumber);
}

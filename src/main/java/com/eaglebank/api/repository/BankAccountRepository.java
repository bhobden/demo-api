package com.eaglebank.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eaglebank.api.model.entity.account.AccountEntity;

import java.util.List;

/**
 * Repository interface for accessing and managing {@link AccountEntity} data.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations and custom queries
 * for bank accounts.
 * </p>
 */
public interface BankAccountRepository extends JpaRepository<AccountEntity, String> {

    /**
     * Finds all bank accounts owned by the specified username.
     *
     * @param username the username of the account owner
     * @return a list of {@link AccountEntity} objects owned by the user
     */
    List<AccountEntity> findByOwnerUsername(String username);
}

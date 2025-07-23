package com.eaglebank.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eaglebank.api.model.entity.bankaccount.BankAccountEntity;
import java.util.List;

/**
 * Repository interface for accessing and managing {@link BankAccountEntity} data.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations and custom queries
 * for bank accounts.
 * </p>
 */
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, String> {

    /**
     * Finds all bank accounts owned by the specified username.
     *
     * @param username the username of the account owner
     * @return a list of {@link BankAccountEntity} objects owned by the user
     */
    List<BankAccountEntity> findByOwnerUsername(String username);
}

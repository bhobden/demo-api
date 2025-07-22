package com.eaglebank.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eaglebank.api.model.entity.bankaccount.BankAccountEntity;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, String> {
    List<BankAccountEntity> findByOwnerUsername(String username);
}

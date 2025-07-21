package com.eaglebank.api.repository;

import com.eaglebank.api.model.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, String> {
    List<BankAccountEntity> findByOwnerUsername(String username);
}

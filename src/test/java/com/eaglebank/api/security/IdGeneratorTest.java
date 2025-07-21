package com.eaglebank.api.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorTest {

    @Test
    void generateUserId_matchesFormat() {
        String userId = IdGenerator.generateUserId();
        assertNotNull(userId);
        assertTrue(userId.matches("^usr-[A-Za-z0-9]{8}$"), "User ID should match ^usr-[A-Za-z0-9]{8}$ but was: " + userId);
    }

    @Test
    void generateAccountNumber_matchesFormat() {
        String accountNumber = IdGenerator.generateAccountNumber();
        assertNotNull(accountNumber);
        assertTrue(accountNumber.matches("^01\\d{6}$"), "Account number should match ^01\\d{6}$ but was: " + accountNumber);
    }

    @Test
    void generateSortCode_matchesFormat() {
        String sortCode = IdGenerator.generateSortCode();
        assertNotNull(sortCode);
        assertTrue(sortCode.matches("^\\d{2}-\\d{2}-\\d{2}$"), "Sort code should match ^\\d{2}-\\d{2}-\\d{2}$ but was: " + sortCode);
    }
}
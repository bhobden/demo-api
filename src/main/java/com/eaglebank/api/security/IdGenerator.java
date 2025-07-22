package com.eaglebank.api.security;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utility class for generating random IDs for users, accounts, and sort codes.
 * Ensures generated values match required formats for EagleBank entities.
 */
public class IdGenerator {
    private static final String USER_PREFIX = "usr-";
    private static final String TRANSACTION_PREFIX = "tan-";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int DEFAULT_LENGTH = 8; // Length of random part for user ID
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates a random user ID matching the format ^usr-[A-Za-z0-9]+$.
     * @return A random user ID string, e.g. usr-Ab12CdEf
     */
    public static String generateUserId() {
        return generatePrefixId(USER_PREFIX);
    }

    public static String generatePrefixId(String prefix) {
        String randomPart = IntStream.range(0, DEFAULT_LENGTH)
                .mapToObj(i -> String.valueOf(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length()))))
                .collect(Collectors.joining());
        return prefix + randomPart;
    }

    /**
     * Generates a random account number matching the format ^01\d{6}$.
     * @return A random account number string, e.g. 01234567
     */
    public static String generateAccountNumber() {
        return String.format("01%06d", RANDOM.nextInt(1_000_000));
    }

    /**
     * Generates a unique transaction ID (e.g., tan-abc123).
     * 
     * @return a unique transaction ID
     */
    public static String generateTransactionId() {
        return generatePrefixId(TRANSACTION_PREFIX);
    }

    /**
     * Generates a random sort code in the format NN-NN-NN.
     * @return A random sort code string, e.g. 10-10-10
     */
    public static String generateSortCode() {
        return String.format("%02d-%02d-%02d", RANDOM.nextInt(100), RANDOM.nextInt(100), RANDOM.nextInt(100));
    }
}
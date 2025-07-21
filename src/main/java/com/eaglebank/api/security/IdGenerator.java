package com.eaglebank.api.security;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IdGenerator {
    private static final String PREFIX = "usr-";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int DEFAULT_LENGTH = 8; // You can adjust the length as needed
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateUserId() {
        String randomPart = IntStream.range(0, DEFAULT_LENGTH)
                .mapToObj(i -> String.valueOf(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length()))))
                .collect(Collectors.joining());
        return PREFIX + randomPart;
    }
}
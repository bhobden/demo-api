package com.eaglebank.api.controller;

public class ControllerConstants {
    private ControllerConstants() { 
        // Prevent instantiation
    }

    public static final String API_VERSION = "/v1";
    public static final String USERS_PATH = API_VERSION + "/users";
    public static final String LOGIN_PATH = API_VERSION + "/login";
    public static final String USERS_WITHID_PATH = USERS_PATH + "/{userId}";
    public static final String ACCOUNTS_PATH = API_VERSION + "/accounts";
    public static final String ACCOUNTS_WITHID_PATH = ACCOUNTS_PATH + "/{accountId}";
    public static final String TRANSACTIONS_PATH = ACCOUNTS_WITHID_PATH + "/transactions";
    public static final String TRANSACTIONS_WITHID_PATH = TRANSACTIONS_PATH + "/{transactionId}";
}

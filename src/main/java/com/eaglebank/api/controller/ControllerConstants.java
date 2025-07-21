package com.eaglebank.api.controller;

public class ControllerConstants {
    private ControllerConstants() { 
        // Prevent instantiation
    }

    public static final String API_VERSION = "/v1";
    public static final String USERS_ENDPOINT = API_VERSION + "/users";
    public static final String LOGIN_ENDPOINT = API_VERSION + "/login";
    public static final String USERS_WITHID_ENDPOINT = USERS_ENDPOINT + "/{userId}";
}

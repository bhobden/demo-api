package com.eaglebank.api.model.dto.response;

public class LoginResponse {
    
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public LoginResponse setJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }
}

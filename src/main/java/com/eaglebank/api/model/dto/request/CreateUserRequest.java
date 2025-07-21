package com.eaglebank.api.model.dto.request;

public class CreateUserRequest extends UpdateUserRequest {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
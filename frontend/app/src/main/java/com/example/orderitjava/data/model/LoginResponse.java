package com.example.orderitjava.data.model;

public class LoginResponse {
    private String access;
    private String refresh;

    public String getAccessToken() {
        return access;
    }

    public String getRefreshToken() {
        return refresh;
    }
}

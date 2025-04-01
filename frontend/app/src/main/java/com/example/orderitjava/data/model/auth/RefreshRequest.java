package com.example.orderitjava.data.model.auth;

public class RefreshRequest {
    private final String refresh;

    public RefreshRequest(String refresh) {
        this.refresh = refresh;
    }

    public String getRefresh() {
        return refresh;
    }
}

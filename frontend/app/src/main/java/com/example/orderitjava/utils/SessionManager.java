package com.example.orderitjava.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // Save tokens
    public void saveTokens(String accessToken, String refreshToken) {
        editor.putString("access_token", accessToken);
        editor.putString("refresh_token", refreshToken);
        editor.apply();
    }

    // Get access token
    public String getAccessToken() {
        return prefs.getString("access_token", null);
    }

    // Get refresh token
    public String getRefreshToken() {
        return prefs.getString("refresh_token", null);
    }

    // Clear tokens (for logout)
    public void clearTokens() {
        editor.remove("access_token");
        editor.remove("refresh_token");
        editor.apply();
    }
}

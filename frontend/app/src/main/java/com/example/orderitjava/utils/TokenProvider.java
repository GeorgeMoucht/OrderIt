package com.example.orderitjava.utils;

import android.content.Context;
import com.example.core.auth.TokenManager;
import com.example.core.auth.TokenStorage;

/**
 * A signleton helper class that manages JWT tokens for the session.
 * Stores access token in memory for fast access and syncs with
 * EncryptedSharedPreferences by using {@link SessionManager}
 */
public class TokenProvider implements TokenManager {
    private static TokenProvider instance;
    private final TokenStorage tokenStorage;
//    private final SessionManager sessionManager;

    private String accessToken; // In memory access token
    private String refreshToken;

    private TokenProvider(TokenStorage tokenStorage) {
        this.tokenStorage = tokenStorage;
        loadTokensFromStorage();
//        sessionManager = new SessionManager(context.getApplicationContext());
//        loadTokensFromStorage();
    }

    public static synchronized TokenProvider getInstance(TokenStorage tokenStorage) {
        if (instance == null) {
            instance = new TokenProvider(tokenStorage);
        }
        return instance;
    }

    /**
     * Load tokens from EncryptedSharedPreferences into the memory.
     */
    private void loadTokensFromStorage() {
        accessToken = tokenStorage.getAccessToken();
        refreshToken = tokenStorage.getRefreshToken();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Save tokens to the memory and EncryptedSharedPreferences.
     */
    public void updateTokens(String newAccessToken, String newRefreshToken) {
        this.accessToken = newAccessToken;
        this.refreshToken = newRefreshToken;
        tokenStorage.saveTokens(newAccessToken, newRefreshToken);
    }

    /**
     * Clears both memory and secure storage tokens.
     */
    public void clearTokens() {
        this.accessToken = null;
        this.refreshToken = null;
        tokenStorage.clearTokens();
    }

    /**
     * Checks if the user is logged in based on presence of tokens.
     */
    public boolean isLoggedIn() {
        return accessToken != null && !accessToken.isEmpty();
    }
}

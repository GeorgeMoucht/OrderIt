package com.example.core.auth;

/**
 * Interface for managing authentication tokens (access + refresh).
 * Abstracts away storage mechanism so modules can remain decoupled.
 */
public interface TokenManager {
    String getAccessToken();
    String getRefreshToken();
    void updateTokens(String accessToken, String refreshToken);
    void clearTokens();
    boolean isLoggedIn();
}

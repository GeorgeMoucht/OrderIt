package com.example.orderitjava.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.security.GeneralSecurityException;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.core.auth.TokenStorage;


/**
 * Secure SessionManager that uses
 * EncryptedSharedPreferences to store JWT tokens safely.
 * <p>
 * This implementation encrypts both keys and values at rest using
 * AES-256 and stores the encryption key in the Android Keystore
 * system.
 * </p>
 *
 * Usage:
 * <pre>
 *     SessionManager sessionManager = new SessionManager(context);
 *     sessionManager.saveTokens(accessToken, refreshToken);
 * </pre>
 *
 * Requirements:
 * <ul>
 *     <li>Android 6.0+ (API 23+)</li>
 *     <li>Dependency: androidx.security:security-crypto</li>
 * </ul>
 */
public class SessionManager implements TokenStorage {
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    /**
     * Initializes the encrypted SharedPreferences using AndroidX
     * security.
     *
     * @param context Application or activity context
     */
    public SessionManager(Context context) {
        try {
            // Build or retrieve the master key used for the encryption.
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            prefs = EncryptedSharedPreferences.create(
                    context,
                    "secure_user_prefs",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            editor = prefs.edit();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Failed to initialize EncryptedSharedPreferences", e);
        }
    }

    /**
     * Stores access and refresh tokens scurely.
     *
     * @param accessToken JWT access token
     * @param refreshToken JWT refresh token
     */
    public void saveTokens(String accessToken, String refreshToken) {
        editor.putString("access_token", accessToken);
        editor.putString("refresh_token", refreshToken);
        editor.apply();
    }

    /**
     * Retrieves the stored acces token.
     *
     * @return Access token or null if not found at all.
     */
    public String getAccessToken() {
        return prefs.getString("access_token", null);
    }

    /**
     * Retrieves the stored refresh token.
     *
     * @return Refresh token or null if not found at all.
     */
    public String getRefreshToken() {
        return prefs.getString("refresh_token", null);
    }

    /**
     * Clears both tokens (logout functionality)
     */
    public void clearTokens() {
        editor.remove("access_token");
        editor.remove("refresh_token");
        editor.apply();
    }
}
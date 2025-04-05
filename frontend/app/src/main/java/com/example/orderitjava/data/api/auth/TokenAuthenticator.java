package com.example.orderitjava.data.api.auth;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.orderitjava.OrderItApplication;
import com.example.orderitjava.data.model.auth.LoginResponse;
import com.example.orderitjava.data.repository.AuthRepository;
import com.example.orderitjava.utils.SessionManager;
import com.example.orderitjava.utils.TokenProvider;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Authenticator triggered automatically by OkHttp when a 401 response is received.
 * It attempts to refresh the access token using the saved refresh token.
 *
 * If successful, it updates the stored tokens and retries the original request.
 * If refresh fails, the authentication flow ends and the user is expected to re-login.
 */
public class TokenAuthenticator implements Authenticator {

    @Nullable
    @Override
    public Request authenticate(
            @Nullable Route route,
            @NonNull Response response
    ) throws IOException {

        Context context = OrderItApplication.getAppContext();
        SessionManager sessionManager = new SessionManager(context);
        String refreshToken = sessionManager.getRefreshToken();

        if (refreshToken == null || refreshToken.isEmpty()) {
            return  null;
        }

        AuthRepository authRepository = new AuthRepository();
        LoginResponse newTokens = authRepository.refreshTokenSync(refreshToken);

        if (newTokens == null) {
            return null;
        }

        // Save Tokens
        sessionManager.saveTokens(newTokens.getAccessToken(), newTokens.getRefreshToken());
        TokenProvider.getInstance(context).updateTokens(
                newTokens.getAccessToken(),
                newTokens.getRefreshToken()
        );

        // Retry original request with new token
        return response.request().newBuilder()
                .header("Authorization", "Bearer " + newTokens.getAccessToken())
                .build();
    }
}
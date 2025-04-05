package com.example.orderitjava.data.remote.auth;

import androidx.lifecycle.LiveData;

import com.example.orderitjava.data.api.ApiService;
import com.example.orderitjava.data.api.RetrofitClient;
import com.example.orderitjava.data.model.auth.LoginResponse;
import com.example.orderitjava.data.model.auth.RefreshRequest;
import com.example.orderitjava.utils.NetworkUtils;
import com.example.orderitjava.utils.Resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Remote data source for handling authentication-related API calls.
 * This class abstracts away direct Retrofit logic from the repository layer,
 * making testing and scaling easier.
 */
public class AuthRemoteDataSource {

    private final ApiService apiServiceNoAuth;

    public AuthRemoteDataSource() {
        this.apiServiceNoAuth = RetrofitClient.getApiServiceWithoutAuth();
    }

    /**
     * Synchronously refreshes the access token using the refresh token.
     *
     * @param refreshToken The current refresh token
     * @return A new LoginResponse containing access + refresh tokens, or null if failed
     */
    public LoginResponse refreshTokenSync(String refreshToken) {
        try {
            Call<LoginResponse> refreshCall = apiServiceNoAuth.refreshToken(new RefreshRequest(refreshToken));
            Response<LoginResponse> response = refreshCall.execute();

            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public LiveData<Resource<String>> logout(String refreshToken) {
        Map<String, String> body = new HashMap<>();
        body.put("refresh", refreshToken);

        return NetworkUtils.performRequest(
                RetrofitClient.getApiService().logoutUser(body),
                "Logout successful"
        );
    }



}

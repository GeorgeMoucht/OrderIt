package com.example.orderitjava.data.repository;


import androidx.lifecycle.LiveData;

import com.example.orderitjava.data.api.ApiService;
import com.example.orderitjava.data.api.RetrofitClient;
import com.example.orderitjava.data.model.auth.LoginRequest;
import com.example.orderitjava.data.model.auth.LoginResponse;
import com.example.orderitjava.data.model.auth.RefreshRequest;
import com.example.orderitjava.data.remote.auth.AuthRemoteDataSource;
import com.example.orderitjava.utils.NetworkUtils;
import com.example.orderitjava.utils.Resource;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Repository class that handles user authentication-related API calls.
 */
public class AuthRepository {
    private static final String TAG="AuthRepository";
    private final ApiService apiService;
    private final AuthRemoteDataSource remoteDataSource;

    /**
     * Initializes the AuthRepository with a singleton ApiService
     * instance.
     */
    public AuthRepository() {
        apiService = RetrofitClient.getApiService();
        remoteDataSource = new AuthRemoteDataSource();
    }

    /**
     * Sends login credentials to the API and returns the response
     * as LiveData.
     *
     * <p>
     * The result is wrapped in a {@link Resource} to allow UI layers
     * to respond to loading, success, or error states.
     * </p>
     *
     * @param username The user's username.
     * @param password The user's password.
     * @return LiveData containing a Resource with the login result.
     */
    public LiveData<Resource<LoginResponse>> loginUser(
            String username,
            String password
    ) {
        LoginRequest request = new LoginRequest(username, password);
        return NetworkUtils.performRequest(apiService.loginUser(request));
    }

    /**
     * Refresh the JWT access token synchronously using the provided refresh
     * token.
     * <p>
     * This method implement the actual network call to the {@link AuthRemoteDataSource},
     * keeping the repository clean and focused on coordinating data flow.
     * </p>
     *
     * <b>Note:</b> This method performs a synchronous network request and should
     * only be used in a background thread (e.g., by OkHttp's {@link okhttp3.Authenticator}).
     *
     * @param refreshToken The refresh token used to request a new access token.
     * @return A {@link LoginResponse} containing new access and refresh tokens,
     *  or {@code null} if the request failed.
     */
    public LoginResponse refreshTokenSync(String refreshToken) {
        return remoteDataSource.refreshTokenSync(refreshToken);
    }
}

package com.example.auth.repository;


import androidx.lifecycle.LiveData;

import com.example.core.model.auth.LoginRequest;
import com.example.core.model.auth.LoginResponse;
import com.example.core.api.ApiService;
import com.example.core.api.RetrofitClient;
import com.example.core.utils.NetworkUtils;
import com.example.core.utils.Resource;

/**
 * Repository class that handles user authentication-related API calls.
 */
public class AuthRepository {
    private static final String TAG="AuthRepository";
    private final ApiService apiService;

    /**
     * Initializes the AuthRepository with a singleton ApiService
     * instance.
     */
    public AuthRepository() {
        apiService = RetrofitClient.getApiService();
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
}

package com.example.orderitjava.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.orderitjava.data.api.ApiService;
import com.example.orderitjava.data.api.RetrofitClient;
import com.example.orderitjava.data.model.LoginRequest;
import com.example.orderitjava.data.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private static final String TAG = "AuthRepository";
    private final ApiService apiService;

    public AuthRepository() {
        apiService = RetrofitClient.getApiService();
    }

    public LiveData<LoginResponse> loginUser(String username, String password) {
        MutableLiveData<LoginResponse> loginResponseLiveData = new MutableLiveData<>();
        LoginRequest loginRequest = new LoginRequest(username, password);

        Log.d(TAG, "Sending login request: " + username + " | " + password); // Debug log

        apiService.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d(TAG, "Response received, Code: " + response.code()); // Log response status code

                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Login successful!");
                    // Store response in LiveData
                    loginResponseLiveData.postValue(response.body());
                } else {
                    Log.d(TAG, "Login failed: " + response.message());
                    // Notify UI that login failed
                    loginResponseLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, "API request failed: " + t.getMessage());
                // Network failure
                loginResponseLiveData.postValue(null);
            }
        });

        return loginResponseLiveData;
    }
}

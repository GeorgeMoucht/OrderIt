package com.example.orderitjava.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderitjava.R;
import com.example.orderitjava.data.api.ApiService;
import com.example.orderitjava.data.api.RetrofitClient;
import com.example.orderitjava.ui.auth.login.LoginActivity;
import com.example.orderitjava.utils.TokenProvider;
import com.example.orderitjava.utils.SessionManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Dummy HomeActivity for testing authentication flows.
 * <p>
 * This activity displays the current access token (for debugging),
 * provides a button to trigger a protected API call, and another
 * button to logout (clearing tokens and navigating back to LoginActivity).
 * </p>
 */
public class HomeActivity extends AppCompatActivity {

    private TextView tvToken;
    private Button btnProtected;
    private Button btnLogout;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        tvToken = findViewById(R.id.tvToken);
        btnProtected = findViewById(R.id.btnProtected);
        btnLogout = findViewById(R.id.btnLogout);
        sessionManager = new SessionManager(getApplicationContext());

        // Optionally display the current access token (for debugging)
        String token = sessionManager.getAccessToken();
        tvToken.setText(token != null ? "Token: " + token : "No token found");

        // Set up the button to call a protected endpoint
        btnProtected.setOnClickListener(v -> {
            ApiService apiService = RetrofitClient.getApiService();
            Call<ResponseBody> call = apiService.getProtectedData();  // Ensure this endpoint exists in your ApiService

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        try {
                            String result = response.body().string();
                            Toast.makeText(HomeActivity.this, "Protected Data: " + result, Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(HomeActivity.this, "Error reading response", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(HomeActivity.this, "Request failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Set up logout button to clear tokens and return to LoginActivity
        btnLogout.setOnClickListener(v -> {
            // Clear tokens from secure storage and memory
            sessionManager.clearTokens();
            TokenProvider.getInstance(getApplicationContext()).clearTokens();

            // Navigate to LoginActivity and clear back stack
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}

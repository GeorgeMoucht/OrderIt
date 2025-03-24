package com.example.orderitjava;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderitjava.ui.HomeActivity;
import com.example.orderitjava.ui.auth.login.LoginActivity;
import com.example.orderitjava.utils.TokenProvider;

/**
 * ViewModel for handling login-related logic in the authentication flow.
 * <p>
 * Serves as a bridge between the UI layer ({@link LoginActivity}) and the
 * repository/data layer ({@link com.example.orderitjava.data.repository.AuthRepository}). It initiates the login process,
 * processes the result, and exposes the login state via {@link androidx.lifecycle.LiveData}.
 * </p>
 *
 * <h3>Responsibilities:</h3>
 * <ul>
 *     <li>Initiates login requests via {@link com.example.orderitjava.data.repository.AuthRepository}</li>
 *     <li>Updates UI with {@link com.example.orderitjava.utils.Resource} status (loading, success, error)</li>
 *     <li>Saves tokens securely using {@link com.example.orderitjava.utils.SessionManager}</li>
 *     <li>Caches tokens in memory using {@link TokenProvider} for faster access</li>
 * </ul>
 *
 * <p>Both secure and in-memory storage ensure token availability across sessions
 * and fast access during runtime.</p>
 */

public class SplashActivity extends AppCompatActivity {

    /**
     * Initializes the splash screen and schedules navigation to the login screen.
     * @param savedInstanceState Previously saved instance state, if any
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Delay for 2 seconds before moving to LoginActivity
        new Handler().postDelayed(() -> {
            TokenProvider tokenProvider = TokenProvider.getInstance(OrderItApplication.getAppContext());

            Intent intent;
            if (tokenProvider.isLoggedIn()) {
                // Token exist so navigate to home screen
                intent = new Intent(SplashActivity.this, HomeActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, 2000);
    }
}

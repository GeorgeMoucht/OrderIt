package com.example.orderitjava.ui.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.orderitjava.R;
import com.example.orderitjava.data.model.auth.LoginResponse;
import com.example.orderitjava.ui.HomeActivity;
import com.example.orderitjava.ui.auth.BaseAuthActivity;
import com.example.orderitjava.ui.devsettings.ServerSettingsActivity;

/**
 * Activity responsible for handling user login.
 * <p>
 * This screen allows users to enter their credentials and initiates
 * the authentication process by delegating the logic to
 * {@link LoginViewModel}. It observes the login state using
 * {@link com.example.orderitjava.utils.Resource} and provides appropriate
 * UI feedback.
 * </p>
 *
 * Inherits from {@link BaseAuthActivity} to share common auth-related behavior like
 * observing auuth results and handling UI feedback.
 *
 * Features:
 * <ul>
 *     <li>Validates input fields</li>
 *     <li>Delegates login call to the ViewModel</li>
 *     <li>Observes login result using shared login in the base class</li>
 * </ul>
 */
public class LoginActivity extends BaseAuthActivity {
    private EditText etUsername, etPassword;
    private LoginViewModel loginViewModel;


    /**
     * Initializes the login screen UI and sets up event listeners
     * and observers.
     * @param savedInstanceState Previously saved state, if any exists.
     *
     */
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        observeLogin();

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            loginViewModel.login(username, password);
        });

        TextView tvLoginTitle = findViewById(R.id.tvLoginTitle);

        tvLoginTitle.setOnLongClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ServerSettingsActivity.class);
            startActivity(intent);
            return true;
        });


    }

    /**
     * Overide to handle redirection on success login.
     *
     * @param response The login response containing tokens.
     */
    @Override
    protected void onAuthSuccess(LoginResponse response) {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Observes the login result from the ViewModel and delegates response
     * handling to the shared method in {@link BaseAuthActivity}.
     */
    private void observeLogin() {
        observeAuthResult(loginViewModel.getLoginResponse());
    }
}
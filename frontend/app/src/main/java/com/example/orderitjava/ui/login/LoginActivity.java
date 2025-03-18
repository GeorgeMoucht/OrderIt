package com.example.orderitjava.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.orderitjava.R;
import com.example.orderitjava.data.model.LoginResponse;

public class LoginActivity extends AppCompatActivity {
   private EditText etUsername, etPassword;
   private Button btnLogin;
   private LoginViewModel loginViewModel;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_login);

       etUsername = findViewById(R.id.etUsername);
       etPassword = findViewById(R.id.etPassword);
       btnLogin = findViewById(R.id.btnLogin);

       loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

       observeLogin();

       btnLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String username = etUsername.getText().toString().trim();
               String password = etPassword.getText().toString().trim();

               if (username.isEmpty() || password.isEmpty()) {
                   Toast.makeText(
                           LoginActivity.this,
                           "Please fill all fields",
                           Toast.LENGTH_SHORT
                   ).show();
                   return;
               }

               loginViewModel.login(username, password);
           }
       });
   }

    private void observeLogin() {
        loginViewModel.getLoginResponse().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse != null) {
                    Toast.makeText(
                            LoginActivity.this,
                            "Login Successful!",
                            Toast.LENGTH_SHORT
                    ).show();

                    // Navigate to HomeActivity
                    // Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    // startActivity(intent);
                    // finish();
                } else {
                    Toast.makeText(
                            LoginActivity.this,
                            "Invalid Credentials",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }
}

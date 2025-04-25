package com.example.orderitjava.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderitjava.R;
import com.example.orderitjava.ui.auth.login.LoginActivity;
import com.example.orderitjava.utils.SessionManager;
import com.example.orderitjava.utils.TokenProvider;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        TextView tokenView = findViewById(R.id.tvToken);
        Button btnLogout = findViewById(R.id.btnLogout);

//        String accessToken = TokenProvider.getInstance(getApplicationContext()).getAccessToken();
        String accessToken = TokenProvider
                .getInstance(new SessionManager(getApplicationContext()))
                .getAccessToken();
        tokenView.setText(accessToken != null ? accessToken : "No token found");

        btnLogout.setOnClickListener(v -> {
            TokenProvider
                    .getInstance(new SessionManager(getApplicationContext()))
                    .clearTokens();

            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear back stack
            startActivity(intent);
        });
    }
}

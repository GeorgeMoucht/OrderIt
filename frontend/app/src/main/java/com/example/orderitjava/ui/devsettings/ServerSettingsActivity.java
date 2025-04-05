package com.example.orderitjava.ui.devsettings;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderitjava.R;
public class ServerSettingsActivity extends AppCompatActivity{
    private EditText etBaseUrl;
    private Button btnSave;

    private static final String PREFS_NAME = "AppSettings";
    private static final String KEY_BASE_URL = "BASE_URL";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devsettings);

        etBaseUrl = findViewById(R.id.etBaseUrl);
        btnSave = findViewById(R.id.btnSave);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        etBaseUrl.setText(prefs.getString(KEY_BASE_URL, "http://192.168.0.107:8000/api/"));

        btnSave.setOnClickListener(v -> {
            String newUrl = etBaseUrl.getText().toString().trim();
            prefs.edit().putString(KEY_BASE_URL, newUrl).apply();
            Toast.makeText(this, "URL αποθηκεύτηκε!", Toast.LENGTH_SHORT).show();
        });
    }
}

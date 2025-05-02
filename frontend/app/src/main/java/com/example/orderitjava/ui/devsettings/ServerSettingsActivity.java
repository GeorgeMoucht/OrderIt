package com.example.orderitjava.ui.devsettings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.orderitjava.R;
import com.example.core.api.RetrofitClient;
import com.example.orderitjava.ui.auth.login.LoginActivity;



public class ServerSettingsActivity extends AppCompatActivity {
    private EditText etBaseUrl;
    private Button btnSave;

    private ServerSettingsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devsettings);

        etBaseUrl = findViewById(R.id.etBaseUrl);
        btnSave = findViewById(R.id.btnSave);

        viewModel = new ViewModelProvider(this).get(ServerSettingsViewModel.class);

        viewModel.getUrl().observe(this, url -> etBaseUrl.setText(url));

        viewModel.getSaveSuccess().observe(this, success -> {
            if (success) {
//                RetrofitClient.reinitialize();
                String url = viewModel.getLastSavedUrl();
                RetrofitClient.reinitialize(url);
                Toast.makeText(this, "URL αποθηκεύτηκε!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Μη έγκυρο URL!", Toast.LENGTH_LONG).show();
            }
        });

        btnSave.setOnClickListener(v -> {
            String newUrl = etBaseUrl.getText().toString().trim();
            viewModel.updateUrl(newUrl);
        });
    }
}
package com.example.orderitjava.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.orderitjava.data.model.LoginResponse;
import com.example.orderitjava.data.repository.AuthRepository;

public class LoginViewModel extends ViewModel {
    private AuthRepository authRepository;

    public LoginViewModel() {
        authRepository = new AuthRepository();
    }

    public LiveData<LoginResponse> login(String username, String password) {
        return authRepository.loginUser(username, password);
    }
}

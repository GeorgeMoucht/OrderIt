package com.example.orderitjava.ui.login;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.orderitjava.data.model.LoginResponse;
import com.example.orderitjava.data.repository.AuthRepository;
import com.example.orderitjava.utils.SessionManager;

public class LoginViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final MediatorLiveData<LoginResponse> loginResponseLiveData = new MediatorLiveData<>();
    private final SessionManager sessionManager;

    public LoginViewModel(Application application) {
        super(application);
        authRepository = new AuthRepository();
        sessionManager = new SessionManager(application);
    }

    public void login(String username, String password) {
        LiveData<LoginResponse> apiResponse = authRepository.loginUser(username, password);

        loginResponseLiveData.addSource(apiResponse, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse != null) {
                    // Store tokens
                    sessionManager.saveTokens(
                            loginResponse.getAccessToken(),
                            loginResponse.getRefreshToken()
                    );
                }
                loginResponseLiveData.setValue(loginResponse);
                loginResponseLiveData.removeSource(apiResponse);
            }
        });
    }

    public LiveData<LoginResponse> getLoginResponse() {
        return loginResponseLiveData;
    }
}

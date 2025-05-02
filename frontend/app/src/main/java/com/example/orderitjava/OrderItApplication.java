package com.example.orderitjava;

import android.app.Application;
import android.content.Context;

import com.example.core.api.RetrofitClient;
import com.example.core.utils.UrlProvider;
import com.example.orderitjava.utils.SessionManager;
import com.example.orderitjava.utils.TokenProvider;

public class OrderItApplication extends Application {
    private static OrderItApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Init RetrofitClient with base URL and TokenProvider
        String baseUrl = UrlProvider.getBaseUrl(this);
        SessionManager sessionManager = new SessionManager(this);
        TokenProvider tokenProvider = TokenProvider.getInstance(sessionManager);
        RetrofitClient.initialize(baseUrl, tokenProvider);
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }
}

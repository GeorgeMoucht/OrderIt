package com.example.core.api;

import android.util.Log;

//import com.example.orderitjava.utils.TokenProvider;
import com.example.core.auth.TokenManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static ApiService apiService = null;
    private static String baseUrl = null;
    private static TokenManager tokenManager = null;

    public static void initialize(String url, TokenManager manager) {
        baseUrl = url;
        tokenManager = manager;
        initRetrofit();
    }

    private static void initRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                message -> Log.d("Retrofit", message)
        );
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor tokenInterceptor = chain -> {
            String token = tokenManager != null ? tokenManager.getAccessToken() : null;

            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Content-Type", "application/json");

            if (token != null) {
                requestBuilder.header("Authorization", "Bearer " + token);
            }

            return chain.proceed(requestBuilder.build());
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(tokenInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        apiService = retrofit.create(ApiService.class);
        Log.d("RetrofitClient", "Retrofit initialized with BASE_URL: " + baseUrl);
    }

    public static void reinitialize(String newUrl) {
        baseUrl = newUrl;
        initRetrofit();
    }

    public static ApiService getApiService() {
        if (apiService == null) {
            throw new IllegalStateException("RetrofitClient not initialized. Call initialize() first.");
        }
        return apiService;
    }
}

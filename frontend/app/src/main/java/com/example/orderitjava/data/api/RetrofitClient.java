package com.example.orderitjava.data.api;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.orderitjava.OrderItApplication;
import com.example.orderitjava.utils.SessionManager;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:8000/api/";
    private static Retrofit retrofit = null;
    private static SessionManager sessionManager; // ✅ Initialize only once

    static {
        initRetrofit();
    }

    private static void initRetrofit() {
        // ✅ Initialize SessionManager once (instead of creating a new instance for every request)
        sessionManager = new SessionManager(OrderItApplication.getAppContext());

        // ✅ Logging Interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                message -> Log.d("Retrofit", message)
        );
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // ✅ Token Interceptor
        Interceptor tokenInterceptor = new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                String token = sessionManager.getAccessToken();

                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Content-Type", "application/json");

                // Attach JWT token only if it exists
                if (token != null) {
                    requestBuilder.header("Authorization", "Bearer " + token);
                }

                return chain.proceed(requestBuilder.build());
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(tokenInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}

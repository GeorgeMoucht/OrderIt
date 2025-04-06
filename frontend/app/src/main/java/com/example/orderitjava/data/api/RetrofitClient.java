package com.example.orderitjava.data.api;

import android.util.Log;
import com.example.orderitjava.OrderItApplication;
import com.example.orderitjava.data.api.auth.TokenAuthenticator;
import com.example.orderitjava.utils.TokenProvider;
import com.example.orderitjava.utils.UrlProvider;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class responsible for initializing and providing
 * the Retrofit client instance.
 * <p>
 * Automatically attaches a JWT access token (if available) to all
 * outgoing HTTP requests using an OkHttp interceptor.
 * Also logs request and response details using an HTTP logging interceptor
 * for debugging purposes.
 * </p>
 *
 * <h3>Usage:</h3>
 * <pre>
 *     ApiService api = RetrofitClient.getApiService();
 * </pre>
 *
 * <h3>Key Features:</h3>
 * <ul>
 *     <li>
 *         Automatically injects the access token from {@link TokenProvider}
 *         into the "Authorization" header (if available).
 *     </li>
 *     <li>
 *         Ensures secure token access using in-memory caching backed by
 *         {@link com.example.orderitjava.utils.SessionManager} and
 *         {@link androidx.security.crypto.EncryptedSharedPreferences}.
 *     </li>
 *     <li>
 *         Provides detailed request/response logging via {@link HttpLoggingInterceptor}.
 *     </li>
 *     <li>
 *         Requires {@link OrderItApplication} to be registered in AndroidManifest.xml
 *         for application-wide context access.
 *     </li>
 * </ul>
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static ApiService apiService = null;

    public static void initRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                message -> Log.d("Retrofit", message)
        );
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor tokenInterceptor = chain -> {
            String token = TokenProvider.getInstance(OrderItApplication.getAppContext())
                    .getAccessToken();

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
                .authenticator(new TokenAuthenticator())
                .build();

        String baseUrl = UrlProvider.getBaseUrl(OrderItApplication.getAppContext());
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        // Refresh apiService
        apiService = retrofit.create(ApiService.class);

        Log.d("RetrofitClient", "Retrofit reinitialized with BASE_URL: " + baseUrl);
    }

    public static ApiService getApiService() {
        if (apiService == null) {
            initRetrofit();
        }
        return apiService;
    }

    public static ApiService getApiServiceWithoutAuth() {
        OkHttpClient noAuthClient = new OkHttpClient.Builder().build();

        Retrofit refreshRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(noAuthClient)
                .build();

        return refreshRetrofit.create(ApiService.class);
    }
}


package com.example.orderitjava.data.api;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.orderitjava.OrderItApplication;
import com.example.orderitjava.utils.TokenProvider;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    private static final String BASE_URL = "http://10.0.2.2:8000/api/";
    private static Retrofit retrofit = null;

    static {
        initRetrofit();
    }

    private static void initRetrofit() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                message -> Log.d("Retrofit", message)
        );
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor tokenInterceptor = new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                String token = TokenProvider.getInstance(OrderItApplication
                        .getAppContext())
                        .getAccessToken();

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

    /**
     * Returns a singleton instance of the {@link ApiService} interface,
     * which defines the API endpoint used in the app.
     *
     * @return ApiService instance
     */
    public static ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}

package com.example.orderitjava.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Utility class to handle Retrofit network calls in a generic way.
 * <p>
 * Wraps the result into a {@link Resource} object that represents loading,
 * success, or error states. This allows consistent UI handling of API
 * responses across the app.
 *
 * Usage:
 * <pre>
 *     LiveData&lt;Resource&lt;MyResponse&gt;&gt; result = NetworkUtils.performRequest(apiService.myCall());
 * </pre>
 *
 * Supports both data responses and void responses (e.g. logout).
 *
 * @author Georgios Mouchtaridis
 */
public class NetworkUtils {

    /**
     * Executes a Retrofit call asynchronously and returns a LiveData stream
     * that emits loading, success or error as a {@link Resource}.
     *
     * @param call The Retrofit call to perform (with a data response).
     * @param <T> The response type expected from the call.
     * @return LiveData containing the Resource-wrapped response.
     */
    public static <T> LiveData<Resource<T>> performRequest(Call<T> call) {
        MutableLiveData<Resource<T>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.isSuccessful()) {
                    result.setValue(Resource.success(response.body()));
                } else {
                    result.setValue(Resource.error("Error: " + response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                result.setValue(Resource.error("Network error: " + t.getMessage()));
            }
        });

        return result;
    }

    /**
     * Executes a Retrofit call asynchronously where no response body is expected
     * (e.g. for logout endpoints that return 204 No Content).
     *
     * @param call The Retrofit call (e.g. Call&lt;Void&gt;)
     * @param successMessage The message to emit on successful request.
     * @return LiveData containing a Resource with the success message or error.
     */
    public static LiveData<Resource<String>> performRequest(Call<Void> call, String successMessage) {
        MutableLiveData<Resource<String>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    result.setValue(Resource.success(successMessage));
                } else {
                    result.setValue(Resource.error("Error: " + response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                result.setValue(Resource.error("Network error: " + t.getMessage()));
            }
        });

        return result;
    }
}

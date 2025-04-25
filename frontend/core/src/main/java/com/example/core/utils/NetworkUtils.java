package com.example.core.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.core.model.BaseResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Utility class to handle Retrofit network calls in a generic way.
 * <p>
 * Wraps the result into a {@link Resource} object that represents loading.
 * This allows consistent UI handling of API responses across the app.
 *
 * Usage:
 * <pre>
 *     LiveData<Resource<MyResponse>> result = NetworkUtils.performRequest(apiService.myCall());
 * </pre>
 *
 * @author Georgios Mouchtaridis
 */
public class NetworkUtils {

    /**
     * Executes a Retrofit call asynchronously and retirns a LiveData stream
     * that emits loading, success or error as a {@link Resource}.
     *
     * @param call The Retrofit call to perform.
     * @param <T> The response type expected from the call.
     * @return LiveData containing the Resource-wrapped response.
     */
    public static <T> LiveData<Resource<T>> performRequest(Call<T> call) {
        MutableLiveData<Resource<T>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.isSuccessful() && response.body() != null) {
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

    public static <T> LiveData<Resource<T>> performWrappedRequest(Call<BaseResponse<T>> call) {
        MutableLiveData<Resource<T>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        call.enqueue(new Callback<BaseResponse<T>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<T>> call, @NonNull Response<BaseResponse<T>> response) {
                Log.d("NetworkUtils", "onResponse: isSuccessful = " + response.isSuccessful());

                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        Log.d("NetworkUtils", "Data size = " + ((List<?>) response.body().getData()).size());
                        result.setValue(Resource.success(response.body().getData()));
                    } else {
                        result.setValue(Resource.error("Server returned success=false"));
                    }
                } else {
                    result.setValue(Resource.error("HTTP " + response.code() + ": " + response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<T>> call, @NonNull Throwable t) {
                result.setValue(Resource.error("Network error: " + t.getMessage()));
            }
        });

        return result;
    }

}

package com.example.orderitjava.data.api;

import com.example.orderitjava.data.model.auth.LoginRequest;
import com.example.orderitjava.data.model.auth.LoginResponse;
import com.example.orderitjava.data.model.auth.RefreshRequest;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("authentication/login/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("authentication/refresh/")
    Call<LoginResponse> refreshToken(@Body RefreshRequest refreshRequest);

    @POST("authentication/logout/")
    Call<Void> logoutUser(@Body Map<String, String> body);

    @GET("authentication/protected")
    Call<ResponseBody> getProtectedData();
}

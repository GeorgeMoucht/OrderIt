package com.example.orderitjava.data.api;

import com.example.orderitjava.data.model.LoginRequest;
import com.example.orderitjava.data.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("authentication/login/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}

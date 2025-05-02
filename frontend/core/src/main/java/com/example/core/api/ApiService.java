package com.example.core.api;

import com.example.core.model.auth.LoginRequest;
import com.example.core.model.auth.LoginResponse;
import com.example.core.model.BaseResponse;
import com.example.core.model.tables.Table;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface ApiService {
    @POST("authentication/login/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("tables/")
    Call<BaseResponse<List<Table>>> getTables();
}

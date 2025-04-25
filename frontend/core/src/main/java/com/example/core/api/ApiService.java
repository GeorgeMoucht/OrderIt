package com.example.orderitjava.data.api;

import com.example.orderitjava.data.model.auth.LoginRequest;
import com.example.orderitjava.data.model.auth.LoginResponse;
import com.example.orderitjava.data.model.tables.Table;
import com.example.orderitjava.data.model.tables.TableResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface ApiService {
    @POST("authentication/login/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET("tables/")
    Call<TableResponse> getTables();
}

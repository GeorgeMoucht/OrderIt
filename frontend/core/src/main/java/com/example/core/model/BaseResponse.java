package com.example.core.model;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
    @SerializedName("success")
    private boolean success;
    @SerializedName("data")
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }
}

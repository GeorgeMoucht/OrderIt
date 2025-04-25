package com.example.orderitjava.data.model;

public class BaseResponse<T> {
    private boolean success;
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }
}

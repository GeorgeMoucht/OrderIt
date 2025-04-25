package com.example.orderitjava.data.repository;

import androidx.lifecycle.LiveData;

import com.example.core.api.RetrofitClient;
import com.example.orderitjava.data.model.tables.Table;
import com.example.core.utils.NetworkUtils;
import com.example.core.utils.Resource;

import java.util.List;

public class TableRepository {
    public LiveData<Resource<List<Table>>> getTables() {
        return NetworkUtils.performWrappedRequest(
                RetrofitClient.getApiService().getTables()
        );
    }
}
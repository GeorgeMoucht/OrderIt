package com.example.orderitjava.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.orderitjava.data.api.RetrofitClient;
import com.example.orderitjava.data.model.tables.Table;
import com.example.orderitjava.utils.NetworkUtils;
import com.example.orderitjava.utils.Resource;

import java.util.List;

public class TableRepository {

    public LiveData<Resource<List<Table>>> getTables() {
        return NetworkUtils.performRequest(RetrofitClient.getApiService().getTables());
    }
}

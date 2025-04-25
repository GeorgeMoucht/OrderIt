package com.example.core.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.core.api.RetrofitClient;
import com.example.core.model.tables.Table;
import com.example.core.utils.NetworkUtils;
import com.example.core.utils.Resource;

import java.util.List;

public class TableRepository {
    public LiveData<Resource<List<Table>>> getTables() {
        Log.d("Repository", "getTables() called");
        return NetworkUtils.performWrappedRequest(
                RetrofitClient.getApiService().getTables()
        );
    }
}
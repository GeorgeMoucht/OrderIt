package com.example.orderitjava.ui.tables;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.core.model.tables.Table;
import com.example.core.repository.TableRepository;
import com.example.core.utils.Resource;

import java.util.List;

public class TableViewModel extends AndroidViewModel {
    private final TableRepository repository;
    private final MediatorLiveData<Resource<List<Table>>> tables = new MediatorLiveData<>();

    public TableViewModel(@NonNull Application application) {
        super(application);
        repository = new TableRepository();
    }

    public LiveData<Resource<List<Table>>> getTables() {
        return tables;
    }

    public void fetchTables() {
        Log.d("ViewModel", "fetchTables() called");
        LiveData<Resource<List<Table>>> source = repository.getTables();

        tables.addSource(source, result -> {
            Log.d("ViewModel", "Result received: " + result.status);

            tables.setValue(result);

            if (result.status == Resource.Status.SUCCESS || result.status == Resource.Status.ERROR) {
                tables.removeSource(source);
            }
        });
    }

}
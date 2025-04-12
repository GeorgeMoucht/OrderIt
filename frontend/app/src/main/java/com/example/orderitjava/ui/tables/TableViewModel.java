package com.example.orderitjava.ui.tables;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.orderitjava.data.model.tables.Table;
import com.example.orderitjava.data.repository.TableRepository;
import com.example.orderitjava.utils.Resource;

import java.util.List;

/**
 * ViewModel responsible for fetching and exposing table data to the UI.
 * Handles the communication between the UI (TableListActivity) and
 * the data layer (TableRepository).
 */
public class TableViewModel extends AndroidViewModel {

    private final TableRepository repository;
    private final MutableLiveData<Resource<List<Table>>> tables = new MutableLiveData<>();

    public TableViewModel(@NonNull Application application) {
        super(application);
        repository = new TableRepository();
    }

    public LiveData<Resource<List<Table>>> getTables() {
        return tables;
    }

    /**
     * Triggers a network call to fetch tables from the backend.
     * Updates the LiveData with loading, success or error state.
     */
    public void fetchTables() {
        repository.getTables().observeForever(resource -> tables.setValue(resource));
    }
}

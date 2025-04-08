package com.example.orderitjava.ui.devsettings;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.orderitjava.utils.AppSettingsManager;

/**
 * ViewModel for the Server Settings screen.
 * <p>
 * This class manages the application base URL used for API requests.
 * It provides observable LiveData to update the UI with the current base URL
 * and the result of save operations.
 * </p>
 *
 * <h3>Responsibilities:</h3>
 * <ul>
 *     <li>Loads the current base URL from SharedPreferences (via {@link AppSettingsManager})</li>
 *     <li>Validates and saves new base URLs</li>
 *     <li>Exposes observable fields to notify the UI on success/failure</li>
 * </ul>
 *
 * <h3>Usage:</h3>
 * <pre>{@code
 * ServerSettingsViewModel viewModel = new ViewModelProvider(this).get(ServerSettingsViewModel.class);
 * viewModel.getUrl().observe(this, url -> { ... });
 * viewModel.getSaveSuccess().observe(this, success -> { ... });
 * }</pre>
 *
 */
public class ServerSettingsViewModel extends AndroidViewModel {

    private final AppSettingsManager settingsManager;

    private final MutableLiveData<String> urlLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> saveSuccess = new MutableLiveData<>();


    /**
     * Constructs the ViewModel and initializes the AppSettingsManager.
     * Loads the current base URL into LiveData.
     *
     * @param application Application context required for accessing SharedPreferences
     */
    public ServerSettingsViewModel(@NonNull Application application) {
        super(application);
        settingsManager = new AppSettingsManager(application);
        urlLiveData.setValue(settingsManager.getBaseUrl());
    }

    /**
     * Returns a LiveData object containing the current base URL.
     *
     * @return LiveData<String> representing the stored base URL
     */
    public LiveData<String> getUrl() {
        return urlLiveData;
    }

    /**
     * Returns a LiveData object indicating the success or failure of
     * saving a new URL.
     *
     * @return LiveData<Boolean> where true = valid and saved, false = invalid
     */
    public LiveData<Boolean> getSaveSuccess() {
        return saveSuccess;
    }

    /**
     * Attempts to update the stored base URL.
     * If the URL is valid, it is saved and success is posted to LiveData.
     * Otherwise, a failure is posted.
     *
     * @param newUrl The new base URL to be saved
     */
    public void updateUrl(String newUrl) {
        if (isValidUrl(newUrl)) {
            settingsManager.setBaseUrl(newUrl);
            saveSuccess.setValue(true);
        } else {
            saveSuccess.setValue(false);
        }
    }

    /**
     * Validates the format of the provided base URL.
     * It must start with http:// or https:// and end with /api/
     *
     * @param url The URL to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidUrl(String url) {
        return (url.startsWith("http://") || url.startsWith("https://")) && url.endsWith("/api/");
    }
}

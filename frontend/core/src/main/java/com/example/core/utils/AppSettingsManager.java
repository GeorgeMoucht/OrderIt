package com.example.core.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Utility class for managing application-wide settings stored in SharedPreferences.
 * <p>
 * This class currently handles the storage and retrieval of the base URL used for
 * network requests via Retrofit. It provides a default value suitable for use
 * with Android emulators if no custom value is set by the user.
 * </p>
 *
 * <h3>Responsibilities:</h3>
 * <ul>
 *     <li>Provides access to persistent app-level settings</li>
 *     <li>Encapsulates SharedPreferences logic</li>
 *     <li>Supplies a default base URL if none is defined</li>
 * </ul>
 *
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * AppSettingsManager settings = new AppSettingsManager(context);
 * String baseUrl = settings.getBaseUrl();
 * settings.setBaseUrl("http://192.168.1.3:8000/api/");
 * }</pre>
 *
 * <h3>Notes:</h3>
 * <ul>
 *     <li>Default value: {@code http://10.0.2.2:8000/api/} (used for emulator access)</li>
 *     <li>Preferences stored under the name {@code AppSettings}</li>
 * </ul>
 *
 */
public class AppSettingsManager {
    private static final String PREFS_NAME = "AppSettings";
    private static final String KEY_BASE_URL = "BASE_URL";
    private static final String DEFAULT_BASE_URL = "http://10.0.2.2:8000/api/";

    private final SharedPreferences prefs;

    /**
     * Constructs a new AppSettingsManager instance.
     *
     * @param context Application or activity context
     */
    public AppSettingsManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Retrieves the currently saved base URL used by the app.
     * If none is saved, returns the default emulator-compatible URL.
     *
     * @return The base URL as a string
     */
    public String getBaseUrl() {
        return prefs.getString(KEY_BASE_URL, DEFAULT_BASE_URL);
    }

    /**
     * Stores the given base URL to SharedPreferences.
     *
     * @param url The new base URL to store
     */
    public void setBaseUrl(String url) {
        prefs.edit().putString(KEY_BASE_URL, url).apply();
    }
}
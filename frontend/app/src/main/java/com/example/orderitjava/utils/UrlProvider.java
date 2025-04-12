package com.example.orderitjava.utils;

import android.content.Context;



/**
 * Utility class that provides the current base URL used for API requests.
 * <p>
 * This class delegates to {@link AppSettingsManager} to retrieve the URL
 * stored in SharedPreferences, or returns the default emulator IP if none is set.
 * </p>
 */
public class UrlProvider {

    /**
     * Returns the base URL to be used for network communication.
     *
     * @param context Application context for accessing settings
     * @return The stored or default base URL
     */
    public static String getBaseUrl(Context context) {
        AppSettingsManager settingsManager = new AppSettingsManager(context);
        return settingsManager.getBaseUrl();
    }
}
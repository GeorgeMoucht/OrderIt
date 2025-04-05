package com.example.orderitjava.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UrlProvider {

    public static String getBaseUrl(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        return prefs.getString("BASE_URL", "http://192.168.0.107:8000/api/");
    }
}

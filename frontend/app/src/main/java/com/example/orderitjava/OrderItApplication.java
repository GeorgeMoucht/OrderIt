package com.example.orderitjava;

import android.app.Application;
import android.content.Context;

public class OrderItApplication extends Application {
    private static OrderItApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }
}

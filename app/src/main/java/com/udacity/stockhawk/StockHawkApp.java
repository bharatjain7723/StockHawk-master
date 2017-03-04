package com.udacity.stockhawk;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

public class StockHawkApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        if (BuildConfig.DEBUG) {
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static Context getContext() {
        return context;
    }

}
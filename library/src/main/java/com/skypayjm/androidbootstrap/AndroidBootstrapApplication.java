package com.skypayjm.androidbootstrap;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Sky on 27/6/2016.
 */
public class AndroidBootstrapApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}

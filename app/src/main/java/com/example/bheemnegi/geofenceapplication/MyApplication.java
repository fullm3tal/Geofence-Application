package com.example.bheemnegi.geofenceapplication;

import android.app.AlertDialog;
import android.app.Application;

import io.nlopez.smartlocation.SmartLocation;

public class MyApplication extends Application {

    public static SmartLocation smartLocation;

    @Override
    public void onCreate() {
        super.onCreate();
        smartLocation = new SmartLocation.Builder(this)
                .logging(true).build();
    }

}

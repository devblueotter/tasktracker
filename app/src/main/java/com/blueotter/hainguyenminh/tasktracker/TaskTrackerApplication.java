package com.blueotter.hainguyenminh.tasktracker;

import android.app.Application;

import com.google.firebase.FirebaseApp;

/**
 * Created by hainguyen on 3/25/18.
 */

public class TaskTrackerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(getApplicationContext());
    }
}

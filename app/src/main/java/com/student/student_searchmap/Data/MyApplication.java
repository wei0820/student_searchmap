package com.student.student_searchmap.Data;

import android.app.Application;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        Firebase.setAndroidContext(this);

    }
}

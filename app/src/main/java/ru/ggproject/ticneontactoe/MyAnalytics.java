package ru.ggproject.ticneontactoe;


import android.app.Application;
import com.google.firebase.FirebaseApp;

public class MyAnalytics extends Application {

    // connecting analytics
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }


}

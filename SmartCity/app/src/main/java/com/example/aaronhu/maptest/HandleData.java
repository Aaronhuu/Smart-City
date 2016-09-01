package com.example.aaronhu.maptest;

import com.firebase.client.Firebase;

/**
 * Created by aaronhu on 8/5/16.
 */
public class HandleData extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

}
package com.breadbin.moviedb_rxjava_example;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by bfahy on 23/01/16.
 */
public class MovieApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(getApplicationContext());
    }
}

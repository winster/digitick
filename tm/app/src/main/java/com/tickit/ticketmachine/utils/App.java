package com.tickit.ticketmachine.utils;

import android.app.Application;

/**
 * Created by wjose on 02/04/2016.
 */
public class App extends Application {
    private static App instance;
    public static App get() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}

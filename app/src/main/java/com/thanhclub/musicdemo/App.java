package com.thanhclub.musicdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by MyPC on 10/01/2018.
 */

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}

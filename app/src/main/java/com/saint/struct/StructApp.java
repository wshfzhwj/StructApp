package com.saint.struct;

import android.app.Application;

import com.saint.struct.tool.CrashHandler;

public class StructApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler crashHandler = new CrashHandler();
//        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }
}

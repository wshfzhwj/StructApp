package com.saint.struct

import android.app.Application
import android.os.Looper
import android.util.Log


class StructApp : Application() {
    override fun onCreate() {
        super.onCreate()
        //        CrashHandler crashHandler = new CrashHandler();
//        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
//        Looper.getMainLooper().setMessageLogging { s -> Log.v("debug", s) }
    }
}
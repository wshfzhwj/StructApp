package com.saint.struct

import android.app.Application
import android.os.Looper
import android.util.Log
import com.saint.struct.tool.CrashHandler


class StructApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        val crashHandler = CrashHandler();
//        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
//        Looper.getMainLooper().setMessageLogging { s -> Log.v("debug", s) }
    }
}
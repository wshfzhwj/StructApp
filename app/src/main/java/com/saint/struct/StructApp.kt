package com.saint.struct

import android.app.Application


class StructApp : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this;
//        val crashHandler = CrashHandler();
//        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
//        Looper.getMainLooper().setMessageLogging { s -> Log.v("debug", s) }
    }

    fun getApplication(): StructApp {
        return application
    }

    companion object {
        lateinit var application: StructApp
    }
}
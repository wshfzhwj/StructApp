package com.saint.struct.tool

import android.os.Looper
import android.util.Log

class CrashHandler : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        Log.e("CrashHandler", "CrashHandler = " + System.currentTimeMillis())
        try {
            //fix No Looper; Looper.prepare() wasn't called on this thread.
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            Looper.loop();
        } catch (e: Exception) {
            uncaughtException(Thread.currentThread(), e);
        }
    }
}

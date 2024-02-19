package com.saint.struct.tool

import android.util.Log

class CrashHandler : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        Log.e("CrashHandler", "CrashHandler = " + System.currentTimeMillis())
    }
}

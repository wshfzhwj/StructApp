package com.saint.struct.tool;

import android.util.Log;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("CrashHandler","CrashHandler = " + System.currentTimeMillis());
    }
}

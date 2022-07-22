package com.saint.struct.service

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class FirstWorkManager (context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {
    override fun doWork(): Result {
        Log.e("TEST", "Checking system。。。。。。。。")
        Thread.sleep(3000)
        Log.e("TEST", "Checking system done.")
        return Result.success()
    }
}
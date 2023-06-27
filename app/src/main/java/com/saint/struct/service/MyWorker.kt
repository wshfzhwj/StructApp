package com.saint.struct.service

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        try {
            Log.e("TEST", "Checking system。。。。。。。。")
            Thread.sleep(3000)
            Log.e("TEST", "Checking system done.")
            return Result.success()
        } catch (e: InterruptedException) {
            return Result.failure()
        }
    }
}
package com.saint.struct.service

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.saint.struct.tool.TAG

class MyWorker(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        try {
            Log.e(TAG, "Checking system。。。。。。。。")
            Thread.sleep(3000)
            Log.e(TAG, "Checking system done.")
            return Result.success()
        } catch (e: InterruptedException) {
            return Result.failure()
        }
    }
}
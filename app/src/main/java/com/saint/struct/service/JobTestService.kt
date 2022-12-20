package com.saint.struct.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.JobIntentService
import com.saint.struct.service.JobTestService
import com.saint.struct.activity.MainActivity

class JobTestService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        val bundle = intent.extras
        Log.e(TAG, bundle!!.getString(MainActivity.EXTRA_KEY_SERVICE)!!)
    }

    companion object {
        val TAG = JobIntentService::class.java.name
        const val JOB_ID = 1
        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, JobTestService::class.java, JOB_ID, work)
        }
    }
}
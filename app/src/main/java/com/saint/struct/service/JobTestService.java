package com.saint.struct.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.saint.struct.activity.MainActivity;

public class JobTestService extends JobIntentService {
    public static final String TAG = JobIntentService.class.getName();
    public static final int JOB_ID = 1;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, JobTestService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.e(TAG, bundle.getString(MainActivity.EXTRA_KEY_SERVICE));
    }
}

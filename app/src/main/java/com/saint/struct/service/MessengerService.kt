package com.saint.struct.service

import android.app.Service
import com.saint.struct.service.MessengerService
import com.saint.struct.service.MessengerService.MyBinder
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MessengerService : Service() {
    inner class MyBinder : Binder() {
        val service: MessengerService
            get() = this@MessengerService
    }

    private val binder = MyBinder()

    //    private Handler messengerHandler = new Handler(getMainLooper()) {
    //        @Override
    //        public void handleMessage(Message msg) {
    //            super.handleMessage(msg);
    //            Log.d(TAG, "MessengerService handler");
    //        }
    //    };
    //
    //    private final Messenger mMessenger = new Messenger(messengerHandler);
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "onBind")
        return binder
        //        return mMessenger.getBinder();
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d(TAG, "onUnbind")
        return false
    }

    companion object {
        private val TAG = MessengerService::class.java.name
    }
}
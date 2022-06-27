package com.sf.struct.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.Nullable;

public class MessengerService extends Service {
    private static final String TAG = MessengerService.class.getName();

    public class MyBinder extends Binder {

        public MessengerService getService() {
            return MessengerService.this;
        }

    }

    private MyBinder binder = new MyBinder();
//    private Handler messengerHandler = new Handler(getMainLooper()) {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Log.d(TAG, "MessengerService handler");
//        }
//    };
//
//    private final Messenger mMessenger = new Messenger(messengerHandler);

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return binder;
//        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return false;
    }
}

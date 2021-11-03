package com.sf.struct.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.Nullable;

public class MessengerService extends Service {
    private static final String TAG = MessengerService.class.getClass().getName();
    private Handler messengerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "MessengerService handler");
        }
    };

    private final Messenger mMessenger = new Messenger(messengerHandler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}

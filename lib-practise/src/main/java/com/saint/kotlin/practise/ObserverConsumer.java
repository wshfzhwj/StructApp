package com.saint.kotlin.practise;

import android.util.Log;

public class ObserverConsumer {
    public void addListener() {
        ObserverPractice practice = new ObserverPractice();
        practice.addObserver(new ObserverInterface() {
            @Override
            public void change() {
                Log.e("", "");
            }
        });
    }
}

package com.saint.kotlin.practise;

import android.util.Log;

public class OberserveConsumer {
    public void addListener() {
        ObeservePractice practice = new ObeservePractice();
        practice.addOber(new OberInterface() {
            @Override
            public void change() {
                Log.e("", "");
            }
        });
    }
}

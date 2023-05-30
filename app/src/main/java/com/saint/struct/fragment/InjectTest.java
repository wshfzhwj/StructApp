package com.saint.struct.fragment;

import android.util.Log;


public class InjectTest {
    @PluginTest
    public void sayHello(){
        Log.e("InjectTest","你好啊 啊啊啊啊");
        System.out.println("你好");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
package com.saint.struct.tool;

import android.util.Log;

import com.saint.annotation.PluginTest;


public class InjectTest {
    @PluginTest
    public void sayHello(){
        System.out.println("你好");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
package com.saint.jnitest;

public class HelloWorld {
    static{
        System.loadLibrary("lib-hello");
    }
    public native int add(int i, int j);
}

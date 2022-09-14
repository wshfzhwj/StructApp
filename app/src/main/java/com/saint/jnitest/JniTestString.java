package com.saint.jnitest;

public class JniTestString {
    static {
        System.loadLibrary("lib-jni");
    }
    public static native String getString();
}

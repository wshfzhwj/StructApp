package com.saint.kotlin.test;

import androidx.annotation.NonNull;

import com.saint.kotlin.test.kotlin.KotlinTest;

public class JavaInvokeKotlin {
    public static void main(@NonNull String[] args) {
        KotlinTest.testStaticMethod();
    }
}

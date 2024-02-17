@file:kotlin.jvm.JvmMultifileClass
package com.saint.struct.tool

import android.os.Build

//     private static final String TAG = "TestClass";

//     private static final String TAG = TestClass.class.getSimpleName();

//     private val TAG = TestClass::class.java.simpleName

//      companion object {
//          private val TAG = TestClass::class.java.simpleName
//      }

//      companion object {
//          @JvmField val TAG: String = TestClass::class.java.simpleName
//      }

//val Any.TAG: String
//    get() {
//        return javaClass.simpleName
//    }

//inline val <reified T> T.TAG: String
//    get() = T::class.java.simpleName

//val Any.TAG: String
//    get() {
//        val name = javaClass.simpleName
//        return if (name.length <= 23 || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) name
//        else name.substring(0, 23)
//    }

//inline val <reified T>T.TAG: String
//    get() {
//        val name = T::class.java.simpleName
//        return if (name.length <= 23 || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) name
//        else name.substring(0, 23)
//    }

//val Any.TAG: String
//    get() {
//        return if (!javaClass.isAnonymousClass) {
//            val name = javaClass.simpleName
//            if (name.length <= 23 || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) name
//            else name.substring(0, 23)
//        } else {
//            val name = javaClass.name
//            if (name.length <= 23 || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) name
//            else name.substring(name.length - 23, name.length)
//        }
//    }
inline val <reified T> T.TAG: String
    get() {
        return if (!T::class.javaClass.isAnonymousClass) {
            val name = T::class.java.simpleName
            if (name.length <= 23 || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) name
            else name.substring(0, 23)
        } else {
            val name = T::class.java.name
            if (name.length <= 23 || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) name
            else name.substring(name.length - 23, name.length)
        }
    }

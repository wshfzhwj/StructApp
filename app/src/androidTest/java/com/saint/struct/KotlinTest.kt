package com.saint.struct

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.junit.Test
import java.io.OutputStream

class KotlinTest {
    @Test
    fun testRxJava() {
        val strings = arrayOf("a", "b", "c")
        val observer: Observer<String> = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {}
            override fun onNext(s: String) {
                Log.e("aaa", "cc=============$s")
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        }
        val observable = Observable.fromArray(*strings)
        observable.subscribe(observer)
    }
}
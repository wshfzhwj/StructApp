package com.saint.struct

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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


        val local = MutableLiveData<String>()
        val remote = MutableLiveData<String>()
        val combine = MediatorLiveData<String>()
        combine.addSource(local) { combine.value = it }
        combine.addSource(remote) { combine.value = it }

    }

    @Test
    fun testLaunch() {
        val scope = CoroutineScope(Dispatchers.Main.immediate)
        Log.d("scope1", Thread.currentThread().name)
        scope.launch{
            Log.d("scope2", Thread.currentThread().name)
        }
        Log.d("scope3", Thread.currentThread().name)
    }
}
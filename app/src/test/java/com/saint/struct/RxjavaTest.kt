package com.saint.struct

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.junit.Test

class RxjavaTest {

    @Test
    fun testRx(){
        val stringObservable = Observable.fromArray("a", "b", "c")
        stringObservable.subscribe { s: String ->
            println(
                "$s "
            )
        }
    }


    @Test
    public fun testRxJava() {
        val strings = arrayOf("张三", "李四", "王五")
        val observer:Observer<String> = object: Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.e("aaa","onSubscribe = ${d.isDisposed}")
            }

            override fun onNext(t: String) {
                Log.e("aaa","onNext = $t")
            }

            override fun onError(e: Throwable) {
                Log.e("aaa","onError = ${e.message}")
            }

            override fun onComplete() {
                Log.e("aaa","onComplete")
            }


        }
        Observable.fromArray("张三", "李四", "王五").subscribe(observer)
        val strArray = arrayOf("张三", "李四", "王五")
        Observable.fromArray(*strArray).subscribe(observer)
    }



    @Test
    public fun testRxJava2() {
        val obe = Observable.fromArray("张三", "李四", "王五")
        val observer: Observer<String> = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                println("onSubscribe = ${d.isDisposed}")
            }

            override fun onNext(t: String) {
                println("onNext = $t")
            }

            override fun onError(e: Throwable) {
                println("onError = ${e.message}")
            }

            override fun onComplete() {
                println("onComplete")
            }


        }
        obe.subscribe(observer)
    }
}

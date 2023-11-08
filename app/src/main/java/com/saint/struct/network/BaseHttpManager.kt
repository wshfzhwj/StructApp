package com.saint.struct.network

import android.util.Log
import androidx.annotation.NonNull
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class BaseHttpManager(debugLog: Boolean, timeOut: Int, url: String) {
    val retrofit: Retrofit

    constructor(timeOut: Int, url: String) : this(false, timeOut, url) {}

    //构造方法私有
    init {
        println("$debugLog $timeOut")
        val interceptor = HttpLoggingInterceptor { message -> Log.i("HttpManager: ", message) }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.retryOnConnectionFailure(true)
        builder.readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS) //设置读取超时时间
        builder.writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS) //设置写的超时时间
        builder.connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS) //设置连接超时时间
        builder.addInterceptor(interceptor)
        builder.connectTimeout(60, TimeUnit.SECONDS)
        retrofit = Retrofit.Builder()
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(url)
            .build()
    }

    fun toSubscribe(o: Flowable<Any>, s: ResourceSubscriber<Any>) {
        o.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(s)
    }

    companion object {
        private const val TIME_OUT = 30
    }
}
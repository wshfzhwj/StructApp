package com.saint.struct.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceCreator {
    private const val BASE_URL = "https://www.wanandroid.com"
    private const val TIME_OUT = 30


    private val interceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor { message -> Log.i("ServiceCreator: ", message) }.setLevel(HttpLoggingInterceptor.Level.BODY)

//    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor { message ->
//        Log.i("ServiceCreator: ", message)
//    }.setLevel(HttpLoggingInterceptor.Level.BODY)

    private val builder = OkHttpClient.Builder().retryOnConnectionFailure(true)
        .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS) //设置读取超时时间
        .writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS) //设置写的超时时间
        .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS) //设置连接超时时间
        .addInterceptor(interceptor)
        .connectTimeout(60, TimeUnit.SECONDS)

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(builder.build()).addConverterFactory(
        GsonConverterFactory.create()
    ).build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}
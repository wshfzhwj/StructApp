package com.saint.struct.network;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseHttpManager {
    private static final int TIME_OUT = 30;

    private Retrofit mRetrofit;

    public BaseHttpManager(int timeOut, String url) {
        this(false, timeOut, url);
    }

    //构造方法私有
    public BaseHttpManager(boolean debugLog, int timeOut, String url) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("HttpManager: ", message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(true);
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);//设置读取超时时间
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);//设置写的超时时间
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);//设置连接超时时间

        builder.addInterceptor(interceptor);
        builder.connectTimeout(60, TimeUnit.SECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public void toSubscribe(Flowable o, ResourceSubscriber s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


}

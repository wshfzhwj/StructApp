package com.saint.struct.network;

import android.util.Log;

import com.saint.struct.network.service.ConnectService;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager extends BaseHttpManager{
    private static final String URL="https://www.wanandroid.com/";
    private static final int TIMEOUT = 60;
    private final ConnectService mService;
    private HttpManager(int timeOut, String url) {
        super(timeOut, url);
        mService = getRetrofit().create(ConnectService.class);
    }

    //默认请求类
    public static HttpManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final HttpManager INSTANCE = new HttpManager(TIMEOUT,
                URL);
    }

    public ConnectService getService() {
        return mService;
    }
}

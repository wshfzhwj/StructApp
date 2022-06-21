package com.sf.struct.network;

import com.sf.struct.network.service.ConnectService;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class HttpManager {
    private static HttpManager sHttpManager;
    private Retrofit mRetrofit;
    private ConnectService mService;
    HttpManager(){
        mRetrofit = new Retrofit.Builder()
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://tieba.baidu.com/")
                .build();
        mService = mRetrofit.create(ConnectService.class);
    }

    public static HttpManager getInstance(){
        if(sHttpManager == null){
            synchronized (HttpManager.class){
                if(sHttpManager == null){
                    sHttpManager = new HttpManager();
                }
            }
        }
        return sHttpManager;
    }

    public Retrofit getRetrofit(){
        return mRetrofit;
    }

    public ConnectService getmService() {
        return mService;
    }

    public void toSubscribe(Flowable o, ResourceSubscriber s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


}

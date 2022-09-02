package com.saint.struct.network;

import com.saint.struct.network.service.ConnectService;

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

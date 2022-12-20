package com.saint.struct.network

import com.saint.struct.network.service.ConnectService


class HttpManager private constructor(timeOut: Int, url: String) : BaseHttpManager(timeOut, url) {
    val service: ConnectService = retrofit.create(ConnectService::class.java)

    private object SingletonHolder {
        //默认请求类
        val instance = HttpManager(TIMEOUT, URL)
    }

    companion object {
        private const val URL = "https://www.wanandroid.com/"
        private const val TIMEOUT = 60
        @JvmStatic
        fun getInstance() = SingletonHolder.instance
    }
}
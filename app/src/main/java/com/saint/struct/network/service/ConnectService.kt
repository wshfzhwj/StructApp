package com.saint.struct.network.service

import retrofit2.http.GET
import com.saint.struct.bean.WanAndroidBean
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Path

interface ConnectService {
    @GET("article/list/{page}/json")
    fun getArticleListRxjava(@Path("page") page: Int): Single<WanAndroidBean>


    @GET("article/list/{page}/json")
    fun getArticleListRxjava2(@Path("page") page: Int): Call<WanAndroidBean>
}
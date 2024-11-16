package com.saint.struct.network.service

import retrofit2.http.GET
import com.saint.struct.bean.WanAndroidBean
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

interface ConnectService {
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int, @Query("cid") cid: Int): Call<WanAndroidBean>

    @GET("article/list/{page}/json")
    fun getArticleList2(@Path("page") page: Int): Single<WanAndroidBean>

    @GET("article/list/{page}/json")
    fun getArticleList3(@Path("page") page: Int): Flowable<WanAndroidBean>

    @GET("article/list/{page}/json")
    fun getArticleList4(@Path("page") page: Int): Observable<WanAndroidBean>
}
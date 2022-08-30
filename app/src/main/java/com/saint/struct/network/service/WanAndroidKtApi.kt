package com.saint.struct.network.service

import com.saint.struct.bean.ProjectResponse
import com.saint.struct.bean.WanAndroidBean
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WanAndroidKtApi {
    @GET("/project/list/{page}/json")
    suspend fun getProjects(@Path("page") page:Int, @Query("cid") id:Int): ProjectResponse

//    @GET("article/list/{page}/json")
//    fun getArticleList2(@Path("page") page: Int): Single<WanAndroidBean?>
}
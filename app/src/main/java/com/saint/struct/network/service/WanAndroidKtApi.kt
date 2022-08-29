package com.saint.struct.network.service

import com.saint.struct.bean.ProjectResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WanAndroidKtApi {
    @GET("/project/list/{page}/json")
    suspend fun getProjects(@Path("page") page:Int, @Query("cid") id:Int): ProjectResponse
}
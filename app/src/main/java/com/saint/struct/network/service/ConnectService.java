package com.saint.struct.network.service;

import com.saint.struct.bean.WanAndroidBean;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConnectService {
    @GET("article/list/{page}/json")
    Call<WanAndroidBean> getArticleList(@Path("page") int page,@Query("cid") int cid);

    @GET("article/list/{page}/json")
    Single<WanAndroidBean> getArticleList2(@Path("page") int page);

    @GET("article/list/{page}/json")
    Flowable<WanAndroidBean> getArticleList3(@Path("page") int page);
}

package com.saint.struct.network.service;

import com.saint.struct.bean.WanAndroidBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConnectService {
//    @GET("api/search/query/listview/category/Android/count/10/page/{page}")
//    Call<List<PageDataBean.ResultsBean>> getArticleList1(@Path("page") int page);

    @GET("article/list/{page}/json")
    Call<WanAndroidBean> getArticleList(@Path("page") int page,@Query("cid") int cid);
}

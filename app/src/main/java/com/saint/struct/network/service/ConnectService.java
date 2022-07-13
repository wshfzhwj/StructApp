package com.saint.struct.network.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ConnectService {
    @GET("/f?kw=赛博朋克2077")
    Call<String> getTxt();

    @GET("/p/7751602453?frwh=index")
    Call<String> getName();
}

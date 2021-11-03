package com.sf.struct.network.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ConnectService {
    @GET()
    Call<String> getTxt();
}

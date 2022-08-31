package com.saint.struct.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.saint.struct.bean.WanAndroidBean;
import com.saint.struct.bean.WanListBean;
import com.saint.struct.network.HttpManager;
import com.saint.struct.network.service.ConnectService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagingOldDataSource extends PageKeyedDataSource<String, WanListBean> {
    private static final String TAG = PagingOldDataSource.class.getSimpleName();
    private int mPage = 0;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, WanListBean> callback) {
        ConnectService api = HttpManager.getInstance().getService();
        Call<WanAndroidBean> call = api.getArticleList(mPage, 60);
        call.enqueue(new Callback<WanAndroidBean>() {
            @Override
            public void onResponse(Call<WanAndroidBean> call, Response<WanAndroidBean> response) {
                Log.e(TAG, "--onResponse-->" + response.body().data.datas.size());
                if (response.isSuccessful() && response.code() == 200) {
                    ArrayList<WanListBean> data = response.body().data.datas;
                    callback.onResult(data, "before", "after");
                }

            }

            @Override
            public void onFailure(Call<WanAndroidBean> call, Throwable t) {
                Log.e(TAG, "--onFailure-->" + t.getMessage());
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, WanListBean> callback) {
        Log.i(TAG, "--loadBefore-->" + params.key);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, WanListBean> callback) {
        mPage++;
        ConnectService api = HttpManager.getInstance().getService();
        Call<WanAndroidBean> call = api.getArticleList(mPage, 60);
        call.enqueue(new Callback<WanAndroidBean>() {
            @Override
            public void onResponse(Call<WanAndroidBean> call, Response<WanAndroidBean> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    ArrayList<WanListBean> data = response.body().data.datas;
                    callback.onResult(data, params.key);
                }

            }

            @Override
            public void onFailure(Call<WanAndroidBean> call, Throwable t) {
                Log.e(TAG, "--onFailure-->" + t.getMessage());
            }
        });
    }

}

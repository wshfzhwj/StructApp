package com.saint.struct.data

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.saint.struct.bean.WanAndroidBean
import com.saint.struct.bean.WanListBean
import com.saint.struct.data.PagingOldDataSource
import com.saint.struct.network.HttpManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PagingOldDataSource : PageKeyedDataSource<String, WanListBean>() {
    private var mPage = 0
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, WanListBean>) {
        val api = HttpManager.getInstance().service
        val call = api.getArticleList(mPage, 60)
        call?.enqueue(object : Callback<WanAndroidBean> {
            override fun onResponse(call: Call<WanAndroidBean>, response: Response<WanAndroidBean>) {
                Log.e(TAG, "--onResponse-->" + response.body()!!.data!!.datas.size)
                if (response.isSuccessful && response.code() == 200) {
                    val data: ArrayList<WanListBean> = response.body()!!.data!!.datas
                    callback.onResult(data, "before", "after")
                }
            }

            override fun onFailure(call: Call<WanAndroidBean>, t: Throwable) {
                Log.e(TAG, "--onFailure-->" + t.message)
            }
        })
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, WanListBean>) {
        Log.i(TAG, "--loadBefore-->" + params.key)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, WanListBean>) {
        mPage++
        val api = HttpManager.getInstance().service
        val call = api.getArticleList(mPage, 60)
        call?.enqueue(object : Callback<WanAndroidBean> {
            override fun onResponse(call: Call<WanAndroidBean>, response: Response<WanAndroidBean>) {
                if (response.isSuccessful && response.code() == 200) {
                    val data: ArrayList<WanListBean> = response.body()!!.data!!.datas
                    callback.onResult(data, params.key)
                }
            }

            override fun onFailure(call: Call<WanAndroidBean>, t: Throwable) {
                Log.e(TAG, "--onFailure-->" + t.message)
            }
        })
    }

    companion object {
        private val TAG = PagingOldDataSource::class.java.simpleName
    }
}
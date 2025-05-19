package com.saint.struct.repository

import android.util.Log
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import com.saint.struct.bean.Project
import com.saint.struct.bean.WanAndroidBean
import com.saint.struct.bean.WanListBean
import com.saint.struct.network.HttpManager
import com.saint.struct.network.ServiceCreator
import com.saint.struct.network.service.WanAndroidKtApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PagingOldRepository(private val repository: Repository) : PagingSource<Int, WanListBean>()  {
    override fun getRefreshKey(state: PagingState<Int, WanListBean>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WanListBean> {
        return try {
            //页码未定义置为1
            val nextPage = params.key ?: 1
            //仓库层请求数据
            val response = repository.getArticleList(nextPage, 60)
            LoadResult.Page(
                data = response.data.datas,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < response.data.pageCount) nextPage + 1 else null
            )
        } catch (e: Exception) {
            Log.e("PagingOldRepository","Exception $e");
            LoadResult.Error(throwable = e)
        }

    }


    companion object {
        private val TAG = PagingOldRepository::class.java.simpleName
    }
}
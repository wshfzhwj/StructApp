package com.saint.struct.repository

import android.util.Log
import androidx.paging.PagingSource.LoadResult.Page.Companion.COUNT_UNDEFINED
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.saint.struct.bean.WanAndroidBean
import com.saint.struct.bean.WanListBean
import com.saint.struct.network.service.ConnectService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class Paging3RxRepository(private val mService: ConnectService) : RxPagingSource<Int, WanListBean>() {
    private var nextPageKey = 0
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, WanListBean>> {
        var nextPageNumber = params.key
        if (nextPageNumber == null) {
            nextPageNumber = 0
        }
        nextPageKey = nextPageNumber
        Log.d(TAG, "Paging3框架，PagingSource loadSingle call,  page = $nextPageKey")
        return mService.getArticleList2(nextPageKey)!!
            .subscribeOn(Schedulers.io())
            .map { bean: WanAndroidBean -> toLoadResult(bean) }
            .onErrorReturn { LoadResult.Error<Int, WanListBean>(Throwable()) }
    }

    private fun toLoadResult(
        bean: WanAndroidBean
    ): LoadResult<Int, WanListBean> {
        return LoadResult.Page<Int, WanListBean>(
            bean.data!!.datas,
            null,  // Only paging forward.
            nextPageKey + 1,
            COUNT_UNDEFINED,
            COUNT_UNDEFINED
        )
    }

    override fun getRefreshKey(state: PagingState<Int, WanListBean>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val (_, prevKey, nextKey) = state.closestPageToPosition(anchorPosition) ?: return null
        if (prevKey != null) {
            return prevKey + 1
        }
        return if (nextKey != null) {
            nextKey - 1
        } else null
    }

    companion object {
        private val TAG = Paging3RxRepository::class.java.simpleName
    }
}
package com.saint.struct.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saint.struct.model.HomeItem
import com.saint.struct.tool.TAG

class CordPagingSource(
    private val repository: HomeRepository
) : PagingSource<Int, HomeItem>() {
    var page : Int = 0
    override fun getRefreshKey(state: PagingState<Int, HomeItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeItem> {
        return try {
            page = params.key ?: 1
            val items = repository.getHomeData(page)
            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Log.e(TAG , "Exception = $e")
            LoadResult.Error(e)
        }
    }
}
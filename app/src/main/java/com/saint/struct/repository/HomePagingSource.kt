package com.saint.struct.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saint.struct.model.HomeItem

class HomePagingSource(
    private val repository: HomeRepository
) : PagingSource<Int, HomeItem>() {

    override fun getRefreshKey(state: PagingState<Int, HomeItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeItem> {
        return try {
            val page = params.key ?: 1
            val items = repository.getHomeData(page)

            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
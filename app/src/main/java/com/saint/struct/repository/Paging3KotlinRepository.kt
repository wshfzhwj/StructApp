package com.saint.struct.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saint.struct.bean.Project
import com.saint.struct.model.HomeItem

class Paging3KotlinRepository(private val repository: Repository) : PagingSource<Int, Project>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Project> {
        return try {
            //页码未定义置为1
            val nextPage = params.key ?: 1
            //仓库层请求数据
            val response = repository.getProjectsList(nextPage, 60)
            LoadResult.Page(
                data = response.data.datas,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < response.data.pageCount) nextPage + 1 else null
            )
        } catch (e: Exception) {
            Log.e("Paging3KotlinRepository","Exception $e");
            LoadResult.Error(throwable = e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Project>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
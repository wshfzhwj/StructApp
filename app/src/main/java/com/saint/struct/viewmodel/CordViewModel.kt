package com.saint.struct.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saint.struct.model.HomeItem
import com.saint.struct.repository.CordPagingSource
import com.saint.struct.repository.HomeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class CordViewModel(private val repository: HomeRepository) : ViewModel() {
    // 添加刷新和加载更多方法
    private val _refreshTrigger = MutableStateFlow(0)
    private val _loadMoreTrigger = MutableStateFlow(0)

    private val pagingSource = MutableStateFlow<CordPagingSource?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val cordData: Flow<PagingData<HomeItem>> = pagingSource.flatMapLatest {
        Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { it ?: CordPagingSource(repository) }
        ).flow.cachedIn(viewModelScope)
    }

    fun refreshData() {
        viewModelScope.launch {
            _refreshState.value = true
            // 强制创建新PagingSource实现刷新
            pagingSource.value = CordPagingSource(repository)
            _refreshState.value = false
        }
    }

    fun loadMoreData() {
        viewModelScope.launch {
            _loadMoreState.value = true
            // 触发PagingSource加载下一页
            pagingSource.value?.invalidate()
            _loadMoreState.value = false
        }
    }

    // 添加状态字段
    private val _refreshState = MutableStateFlow<Boolean>(false)
    val refreshState: StateFlow<Boolean> = _refreshState

    private val _loadMoreState = MutableStateFlow<Boolean>(false)
    val loadMoreState: StateFlow<Boolean> = _loadMoreState
}
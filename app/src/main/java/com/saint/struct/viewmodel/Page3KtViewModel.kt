package com.saint.struct.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.saint.struct.data.Paging3KtDataSource
import com.saint.struct.network.Repository

class Page3KtViewModel : ViewModel() {
    val projects = Pager(PagingConfig(pageSize = 20)) { Paging3KtDataSource(Repository) }
        .flow.cachedIn(viewModelScope)
}
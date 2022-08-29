package com.saint.struct.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.saint.struct.data.ProjectPagingSource
import com.saint.struct.network.Repository

class ArticleViewModel : ViewModel() {
    val projects = Pager(PagingConfig(pageSize = 20)) { ProjectPagingSource(Repository) }
        .flow.cachedIn(viewModelScope)
}
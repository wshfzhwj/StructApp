package com.saint.struct.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.saint.struct.repository.PagingOldRepository
import com.saint.struct.repository.Repository

class PageArticleViewModel : ViewModel() {
    val articles = Pager(PagingConfig(pageSize = 20)) { PagingOldRepository(Repository) }
        .flow.cachedIn(viewModelScope)
}

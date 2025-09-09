package com.saint.struct.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.saint.struct.repository.Paging3KotlinRepository
import com.saint.struct.repository.Repository

class PageProjectViewModel(application: Application) : BaseViewModel(application) {
    val projects = Pager(PagingConfig(pageSize = 20)) { Paging3KotlinRepository(Repository) }
        .flow.cachedIn(viewModelScope)
}
package com.saint.struct.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.saint.struct.StructApp.Companion.application
import com.saint.struct.bean.WanListBean
import com.saint.struct.network.HttpManager
import com.saint.struct.repository.Paging3RxRepository
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class Paging3RxViewModel (application: Application) : BaseViewModel(application)  {
    private var mPager: Pager<Int, WanListBean>
    private var mPaging3RxRepository: Paging3RxRepository

    //rxjava flowable
    var flowable: Flowable<PagingData<WanListBean>>

    init {
        val viewModelScope = this.viewModelScope
        /**
         * 数据源
         */
        mPaging3RxRepository = Paging3RxRepository(HttpManager.getInstance().service)
        /**
         * Pager ：分页大管家, 使用网络数据源构造
         */
        val config = PagingConfig(20)
        mPager = Pager(config, null) { mPaging3RxRepository }
        /**
         * PagingRx.getObservable
         */
        flowable = mPager.flowable
        flowable.cachedIn(viewModelScope)
    }

     class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(Paging3RxViewModel::class.java)) {
                return Paging3RxViewModel(application) as T
            }
            throw IllegalArgumentException("UnKnown class")
        }
    }
}


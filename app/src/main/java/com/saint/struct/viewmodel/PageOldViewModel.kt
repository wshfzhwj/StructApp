package com.saint.struct.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.saint.struct.bean.WanListBean
import com.saint.struct.repository.PagingOldRepository

class PageOldViewModel : ViewModel() {
    private val pageSize = 20

    //PagedList配置
    private val config: PagedList.Config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(pageSize) //设置首次加载的数量
        .setPageSize(pageSize) //设置每页加载的数量
        .setPrefetchDistance(2) //设置距离每页最后数据项来时预加载下一页数据
        .setEnablePlaceholders(false) //设置是否启用UI占用符
        .build()

    //DataSource.Factory
    private val factory: DataSource.Factory<String, WanListBean> = PagingDataSourceFactory()

    //LiveData
    val pagedList: LiveData<PagedList<WanListBean>> = LivePagedListBuilder(factory, config)
        .build()

    internal inner class PagingDataSourceFactory : DataSource.Factory<String, WanListBean>() {
        override fun create(): DataSource<String, WanListBean> {
            return PagingOldRepository()
        }
    }
}

package com.saint.struct.data

import androidx.paging.DataSource
import com.saint.struct.bean.WanListBean

class PagingDataSourceFactory : DataSource.Factory<String, WanListBean>() {
    override fun create(): DataSource<String, WanListBean> {
        return PagingOldDataSource()
    }
}
package com.saint.struct.data;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.saint.struct.bean.WanAndroidBean;

public class PagingDataSourceFactory extends DataSource.Factory<String, WanAndroidBean.WanBean.WanListBean> {

    @NonNull
    @Override
    public DataSource<String, WanAndroidBean.WanBean.WanListBean> create() {
        PagingOldDataSource dataSource = new PagingOldDataSource();
        return dataSource;
    }
}

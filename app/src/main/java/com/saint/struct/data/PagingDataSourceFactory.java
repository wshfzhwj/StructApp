package com.saint.struct.data;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.saint.struct.bean.WanListBean;

public class PagingDataSourceFactory extends DataSource.Factory<String, WanListBean> {

    @NonNull
    @Override
    public DataSource<String, WanListBean> create() {
        PagingOldDataSource dataSource = new PagingOldDataSource();
        return dataSource;
    }
}

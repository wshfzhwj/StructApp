package com.saint.struct.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.saint.struct.bean.WanAndroidBean;
import com.saint.struct.data.PagingDataSourceFactory;

public class PageOldViewModel extends ViewModel {
    private int pageSize = 20;
    //PagedList配置
    private PagedList.Config config = new PagedList.Config.Builder()
            .setInitialLoadSizeHint(pageSize)//设置首次加载的数量
            .setPageSize(pageSize)//设置每页加载的数量
            .setPrefetchDistance(2)//设置距离每页最后数据项来时预加载下一页数据
            .setEnablePlaceholders(false)//设置是否启用UI占用符
            .build();

    //DataSource.Factory
    private DataSource.Factory<String, WanAndroidBean.WanBean.WanListBean> factory = new PagingDataSourceFactory();

    //LiveData
    private LiveData<PagedList<WanAndroidBean.WanBean.WanListBean>> mPagedList = new LivePagedListBuilder<>(factory, config)
            .build();

    public LiveData<PagedList<WanAndroidBean.WanBean.WanListBean>> getPagedList() {
        return mPagedList;
    }
}

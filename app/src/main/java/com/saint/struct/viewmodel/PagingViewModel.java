package com.saint.struct.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.saint.struct.bean.WanAndroidBean;
import com.saint.struct.data.Paging3DataSource;

import kotlinx.coroutines.CoroutineScope;

public class PagingViewModel extends ViewModel {
    PagingConfig pagingConfig = new PagingConfig(20, 3);

    public LiveData<PagingData<WanAndroidBean.WanBean.WanListBean>> getArticleData() {
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Pager<Integer, WanAndroidBean.WanBean.WanListBean> pager = new Pager<>(pagingConfig, () -> new Paging3DataSource());
        LiveData<PagingData<WanAndroidBean.WanBean.WanListBean>> cachedResult = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
        return cachedResult;
    }
}

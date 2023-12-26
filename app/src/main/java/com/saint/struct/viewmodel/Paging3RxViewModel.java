package com.saint.struct.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava2.PagingRx;

import com.saint.struct.bean.WanListBean;
import com.saint.struct.repository.Paging3RxRepository;
import com.saint.struct.network.HttpManager;

import io.reactivex.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class Paging3RxViewModel extends ViewModel {
    Pager<Integer, WanListBean> mPager;
    Paging3RxRepository mPaging3RxRepository;

    //rxjava flowable
    Flowable<PagingData<WanListBean>> flowable;

    public Paging3RxViewModel() {
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        /**
         * 数据源
         */
        mPaging3RxRepository = new Paging3RxRepository(HttpManager.getInstance().getService());
        /**
         * Pager ：分页大管家, 使用网络数据源构造
         */
        PagingConfig config = new PagingConfig(20);
        mPager = new Pager<Integer, WanListBean>(config, () -> mPaging3RxRepository);

        /**
         *  PagingRx.getObservable
         */
        flowable = PagingRx.getFlowable(mPager);
        PagingRx.cachedIn(flowable, viewModelScope);
    }

    public Flowable<PagingData<WanListBean>> getFlowable() {
        return flowable;
    }

    public static class Factory implements ViewModelProvider.Factory {

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(Paging3RxViewModel.class)) {
                return (T) new Paging3RxViewModel();
            }
            throw new IllegalArgumentException("UnKnown class");
        }
    }
}

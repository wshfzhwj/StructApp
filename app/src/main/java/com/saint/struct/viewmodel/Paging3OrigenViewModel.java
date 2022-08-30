package com.saint.struct.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.saint.struct.bean.WanAndroidBean;
import com.saint.struct.data.Paging3DataSource;

import kotlinx.coroutines.CoroutineScope;

public class Paging3OrigenViewModel extends ViewModel {
    PagingConfig pagingConfig = new PagingConfig(20, 3);
    LiveData<PagingData<WanAndroidBean.WanBean.WanListBean>> cachedResult;

    public Paging3OrigenViewModel(Context context) {
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Pager<Integer, WanAndroidBean.WanBean.WanListBean> pager = new Pager<>(pagingConfig, () -> new Paging3DataSource());
        cachedResult = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
    }

    public LiveData<PagingData<WanAndroidBean.WanBean.WanListBean>> getLiveData() {
        return cachedResult;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application mApplication;

        public Factory(@NonNull Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new Paging3OrigenViewModel(mApplication);
        }
    }
}

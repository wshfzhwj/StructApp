package com.saint.struct.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxPagingSource;

import com.saint.struct.bean.WanAndroidBean;
import com.saint.struct.network.HttpManager;
import com.saint.struct.network.service.ConnectService;
import com.saint.struct.network.service.WanAndroidKtApi;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class Paging3RxDataResource extends RxPagingSource<Integer, WanAndroidBean.WanBean.WanListBean> {
    private static final String TAG = Paging3RxDataResource.class.getSimpleName();
    @NonNull
    private ConnectService mService;
    private int nextPageKey = 0;

    public Paging3RxDataResource(@NonNull ConnectService service) {
        mService = service;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, WanAndroidBean.WanBean.WanListBean>> loadSingle(@NonNull LoadParams<Integer> params) {
        Integer nextPageNumber = params.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 0;
        }

        nextPageKey = nextPageNumber;

        Log.d(TAG, "Paging3框架，PagingSource loadSingle call,  page = " + nextPageKey);

        return mService.getArticleList2(nextPageKey)
                .subscribeOn(Schedulers.io())
                .map(this::toLoadResult)
                .onErrorReturn(LoadResult.Error::new);

    }

    private LoadResult<Integer, WanAndroidBean.WanBean.WanListBean> toLoadResult(
            @NonNull WanAndroidBean bean) {
        return new LoadResult.Page<>(
                bean.data.datas,
                null, // Only paging forward.
                nextPageKey + 1,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, WanAndroidBean.WanBean.WanListBean> state) {
        Integer anchorPosition = state.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, WanAndroidBean.WanBean.WanListBean> anchorPage = state.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        return null;
    }
}

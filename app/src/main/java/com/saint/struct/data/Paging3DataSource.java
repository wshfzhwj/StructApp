package com.saint.struct.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingSource;
import androidx.paging.PagingState;

import com.saint.struct.bean.WanAndroidBean;
import com.saint.struct.network.HttpManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import kotlin.coroutines.Continuation;

public class Paging3DataSource extends PagingSource<Integer, WanAndroidBean.WanBean.WanListBean> {
    int page = 0;
    @Nullable
    @Override
    public Object load(@NotNull LoadParams<Integer> params, @NotNull Continuation<? super LoadResult<Integer, WanAndroidBean.WanBean.WanListBean>> continuation) {
        if (params.getKey() != null) {
            page = params.getKey();
        }
        //获取网络数据
        Log.e("Paging3DataSource","load =======================");
        Flowable flowable = HttpManager.getInstance().getService().getArticleList3(page).subscribeOn(Schedulers.io());
        final LoadResult<Integer, WanAndroidBean.WanBean.WanListBean>[] result = new LoadResult[1];
        HttpManager.getInstance().toSubscribe(flowable, new ResourceSubscriber() {
            @Override
            public void onNext(Object o) {
                Log.e("Paging3DataSource","onNext =======================");
                result[0] = toLoadResult((WanAndroidBean) o);
            }

            @Override
            public void onError(Throwable t) {
                Log.e("Paging3DataSource","onError =======================");
                result[0] = new LoadResult.Error<>(t);
            }

            @Override
            public void onComplete() {
                Log.e("Paging3DataSource","onComplete =======================");
            }
        });
        return result[0];
    }

    private LoadResult<Integer, WanAndroidBean.WanBean.WanListBean> toLoadResult(
            @NonNull WanAndroidBean bean) {
        Log.e("Paging3DataSource","toLoadResult=======================");
        LoadResult result = new LoadResult.Page<>(
                bean.data.datas,
                null, // Only paging forward.
                page + 1,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
        return result;
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, WanAndroidBean.WanBean.WanListBean> pagingState) {
        Integer anchorPosition = pagingState.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, WanAndroidBean.WanBean.WanListBean> anchorPage = pagingState.closestPageToPosition(anchorPosition);
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

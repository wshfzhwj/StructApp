package com.saint.struct.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingSource;
import androidx.paging.PagingState;

import com.saint.struct.bean.WanAndroidBean;
import com.saint.struct.network.HttpManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kotlin.coroutines.Continuation;

public class Paging3DataSource extends PagingSource<Integer, WanAndroidBean.WanBean.WanListBean> {
    @Nullable
    @Override
    public Object load(@NotNull LoadParams<Integer> params, @NotNull Continuation<? super LoadResult<Integer, WanAndroidBean.WanBean.WanListBean>> continuation) {
        int page = 0;
        if (params.getKey() != null) {
            page = params.getKey();
        }
        //获取网络数据
        WanAndroidBean result = (WanAndroidBean) HttpManager.getInstance().getService().getArticleList(page, 60);
        //需要加载的数据
        List<WanAndroidBean.WanBean.WanListBean> datas = result.data.datas;
        //如果可以往上加载更多就设置该参数，否则不设置
        String prevKey = null;
        //加载下一页的key 如果传null就说明到底了
        String nextKey = null;
        if (result.data.curPage == result.data.pageCount) {
            nextKey = null;
        } else {
            nextKey = String.valueOf(page + 1);
        }
        return new LoadResult.Page<>(datas, prevKey, nextKey);
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

package com.saint.struct.activity;

import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.saint.struct.viewmodel.Paging3RxViewModel;

public class PageRxActivity extends BasePageJavaActivity {
    private static final String TAG = "PageRxActivity";
    @Override
    void setModelAndData() {
        Paging3RxViewModel.Factory factory = new Paging3RxViewModel.Factory(this.getApplication());
        Paging3RxViewModel viewModel = new ViewModelProvider(this, factory)
                .get(Paging3RxViewModel.class);

        viewModel.getFlowable()
                // Using AutoDispose to handle subscription lifecycle.
                // See: https://github.com/uber/AutoDispose
//            .to(autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(pagingData -> {
                    Log.d(TAG, "pagingData  =====================");
                    getPageAdapter().submitData(getLifecycle(), pagingData);
                });
    }
}

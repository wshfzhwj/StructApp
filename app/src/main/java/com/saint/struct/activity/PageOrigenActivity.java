package com.saint.struct.activity;

import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.saint.struct.viewmodel.Paging3OrigenViewModel;

public class PageOrigenActivity extends BasePageJavaActivity {
    private static final String TAG = "PageOrigenActivity";

    @Override
    protected void setModelAndData() {
        Paging3OrigenViewModel.Factory factory = new Paging3OrigenViewModel.Factory(this.getApplication());
        Paging3OrigenViewModel viewModel = new ViewModelProvider(this, factory)
                .get(Paging3OrigenViewModel.class);

        viewModel.getLiveData().observe(this, wanListBeanPagingData -> {
            Log.d(TAG, "onChanged=====================");
            getPageAdapter().submitData(getLifecycle(), wanListBeanPagingData);
        });
    }
}

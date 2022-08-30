package com.saint.struct.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.saint.struct.R;
import com.saint.struct.adapter.PagingAdapter;
import com.saint.struct.databinding.LayoutPageBinding;

public abstract class BasePageJavaActivity extends BaseActivity{

    private String TAG = "PageOrigenActivity";
    private LayoutPageBinding activityMainBinding;
    private PagingAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.layout_page);
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new PagingAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activityMainBinding.recyclerView.setLayoutManager(layoutManager);
        activityMainBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        activityMainBinding.recyclerView.setAdapter(adapter);

        setModelAndData();
    }
    public PagingAdapter getPageAdapter(){
        return adapter;
    }

    abstract void setModelAndData();
}

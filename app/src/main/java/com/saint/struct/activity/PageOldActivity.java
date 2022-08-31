package com.saint.struct.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.saint.struct.R;
import com.saint.struct.adapter.PagingOldAdapter;
import com.saint.struct.bean.WanListBean;
import com.saint.struct.databinding.LayoutPageBinding;
import com.saint.struct.viewmodel.PageOldViewModel;

public class PageOldActivity extends BaseActivity{
    private String TAG = "PageOldActivity";
    private LayoutPageBinding activityMainBinding;
    private PagingOldAdapter adapter;
    private PageOldViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.layout_page);
        initRecyclerView();
        getData();
    }

    private void initRecyclerView() {
        adapter = new PagingOldAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activityMainBinding.recyclerView.setLayoutManager(layoutManager);
        activityMainBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        activityMainBinding.recyclerView.setAdapter(adapter);

        ViewModelProvider mViewModelProvider = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication()));
        mViewModel = mViewModelProvider.get(PageOldViewModel.class);
    }
    public void getData() {
        mViewModel.getPagedList().observe(this, dataBeans -> adapter.submitList(dataBeans));
    }

}

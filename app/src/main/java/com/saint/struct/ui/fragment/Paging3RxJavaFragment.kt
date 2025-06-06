package com.saint.struct.ui.fragment

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.viewModels
import com.saint.struct.viewmodel.Paging3RxViewModel
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import com.saint.struct.adapter.PagingRxJavaAdapter
import com.saint.struct.bean.WanListBean
import com.saint.struct.databinding.FragmentPagingBinding

private const val TAG = "PageRxActivity"

class Paging3RxJavaFragment : BasePagingFragment<WanListBean, PagingRxJavaAdapter.ViewHolder>() {
    @SuppressLint("CheckResult")
    override fun setModelAndData() {
        val factory = Paging3RxViewModel.Factory()
        val viewModel: Paging3RxViewModel by viewModels { factory }
        viewModel.flowable
            .subscribe { pagingData: PagingData<WanListBean>? ->
                pageAdapter.submitData(lifecycle, pagingData!!)
            }
    }

    override fun setListener() {}

    override fun getCustomPageAdapter(): PagingDataAdapter<WanListBean, PagingRxJavaAdapter.ViewHolder> {
        return PagingRxJavaAdapter()
    }

    @SuppressLint("SetTextI18n")
    override fun initTitle() {
        (fragmentBinding as FragmentPagingBinding).layoutAppBar.titleBar.text = "PageRxFragment"
    }
}
package com.saint.struct.ui.fragment

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.saint.struct.viewmodel.Paging3RxViewModel
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import com.saint.struct.adapter.PagingRxJavaAdapter
import com.saint.struct.bean.WanListBean
import com.saint.struct.databinding.FragmentPagingBinding

private const val TAG = "PageRxActivity"

class Paging3RxJavaFragment :
    BasePagingFragment<FragmentPagingBinding, WanListBean, PagingRxJavaAdapter.ViewHolder>() {
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
        binding.layoutAppBar.titleBar.text = "PageRxFragment"
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPagingBinding {
        return FragmentPagingBinding.inflate(inflater,container,false)
    }
}
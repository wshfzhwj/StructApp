package com.saint.struct.activity

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.saint.struct.viewmodel.Paging3RxViewModel
import androidx.paging.PagingData
import com.saint.struct.bean.WanListBean
import com.saint.struct.activity.PageRxActivity

class PageRxActivity : BasePageJavaActivity() {
    @SuppressLint("CheckResult")
    override fun setModelAndData() {
        val factory = Paging3RxViewModel.Factory(this.application)
        val viewModel = ViewModelProvider(this, factory)
            .get(Paging3RxViewModel::class.java)
        viewModel.flowable // Using AutoDispose to handle subscription lifecycle.
            // See: https://github.com/uber/AutoDispose
            //            .to(autoDisposable(AndroidLifecycleScopeProvider.from(this)))
            .subscribe { pagingData: PagingData<WanListBean>? ->
                Log.d(TAG, "pagingData  =====================")
                pageAdapter!!.submitData(lifecycle, pagingData!!)
            }
    }

    companion object {
        private const val TAG = "PageRxActivity"
    }
}
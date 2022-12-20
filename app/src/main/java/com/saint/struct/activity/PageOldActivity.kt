package com.saint.struct.activity

import com.saint.struct.adapter.PagingOldAdapter
import com.saint.struct.viewmodel.PageOldViewModel
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.saint.struct.R
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saint.struct.bean.WanListBean
import com.saint.struct.databinding.LayoutPageBinding

class PageOldActivity : BaseActivity() {
    companion object{
        private val TAG = "PageOldActivity"
    }

    private lateinit var activityMainBinding: LayoutPageBinding
    private lateinit  var adapter: PagingOldAdapter
    private lateinit var mViewModel: PageOldViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.layout_page)
        initRecyclerView()
        val mViewModelProvider = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )
        mViewModel = mViewModelProvider.get(PageOldViewModel::class.java)
        mViewModel.pagedList.observe(this) { dataBeans: PagedList<WanListBean> -> adapter.submitList(dataBeans) }
    }

    private fun initRecyclerView() {
        adapter = PagingOldAdapter()
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        activityMainBinding.recyclerView.layoutManager = layoutManager
        activityMainBinding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        activityMainBinding.recyclerView.adapter = adapter
    }
}
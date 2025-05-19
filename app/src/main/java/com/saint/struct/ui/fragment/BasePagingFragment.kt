package com.saint.struct.ui.fragment

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saint.struct.BuildConfig
import com.saint.struct.R
import com.saint.struct.databinding.FragmentPagingBinding

abstract class BasePagingFragment<T : Any,VH : RecyclerView.ViewHolder> : BaseFragment() {
    lateinit var pageAdapter:PagingDataAdapter<T,VH>

    override fun doInit() {
        initTitle()
        initRecyclerView()
        setModelAndData()
        setListener()
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_paging
    }

    private fun initRecyclerView() {
        pageAdapter = getCustomPageAdapter()
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        (fragmentBinding as FragmentPagingBinding).recyclerView.apply {
            this.layoutManager = layoutManager
            this.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            this.adapter = pageAdapter
        }
    }

    abstract fun getCustomPageAdapter(): PagingDataAdapter<T, VH>
    abstract fun initTitle()
    abstract fun setModelAndData()
    abstract fun setListener()

}
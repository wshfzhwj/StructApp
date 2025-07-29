package com.saint.struct.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.saint.struct.databinding.FragmentPagingBinding

abstract class BasePagingFragment<VB: ViewBinding,T : Any,VH : RecyclerView.ViewHolder> : BaseFragment<VB>() {
    lateinit var pageAdapter:PagingDataAdapter<T,VH>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitle()
        initRecyclerView()
        setModelAndData()
        setListener()
    }

    override fun initData() {
    }

    private fun initRecyclerView() {
        pageAdapter = getCustomPageAdapter()
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        (binding as FragmentPagingBinding).recyclerView.apply {
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
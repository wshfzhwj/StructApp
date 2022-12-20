package com.saint.struct.activity

import com.saint.struct.adapter.PagingAdapter
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saint.struct.R
import com.saint.struct.databinding.LayoutPageBinding

abstract class BasePageJavaActivity : BaseActivity() {
    companion object {
        private val TAG = "PageOrigenActivity"
    }

    private lateinit var activityMainBinding: LayoutPageBinding
    lateinit var pageAdapter: PagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.layout_page)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        pageAdapter = PagingAdapter()
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        activityMainBinding.recyclerView.layoutManager = layoutManager
        activityMainBinding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        activityMainBinding.recyclerView.adapter = pageAdapter
        setModelAndData()
    }

    abstract fun setModelAndData()
}
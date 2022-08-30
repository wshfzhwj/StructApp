package com.saint.struct.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saint.struct.R
import com.saint.struct.adapter.PagingKtAdapter
import com.saint.struct.databinding.LayoutPageBinding
import com.saint.struct.viewmodel.Page3KtViewModel
import kotlinx.coroutines.flow.collectLatest

class PageKtActivity : BaseActivity() {
    private val TAG = "PageActivity"
    private var activityMainBinding: LayoutPageBinding? = null
    private var adapter: PagingKtAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.layout_page)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = PagingKtAdapter()
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        activityMainBinding!!.recyclerView.layoutManager = layoutManager
        activityMainBinding!!.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        activityMainBinding!!.recyclerView.adapter = adapter

//        val viewModel = ViewModelProvider(this).get(
//            ArticleViewModel::class.java
//        )
//        lifecycleScope.launchWhenCreated {
//            viewModel.projects.collectLatest {
//                adapter?.submitData(lifecycle,it)
//            }
//        }
        val viewModel = ViewModelProvider(this).get(
            Page3KtViewModel::class.java
        )
        lifecycleScope.launchWhenCreated {
            viewModel.projects.collectLatest {
                adapter?.submitData(lifecycle, it)
            }
        }

    }
}
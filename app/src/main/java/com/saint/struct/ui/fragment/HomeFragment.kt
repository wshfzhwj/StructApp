package com.saint.struct.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saint.struct.R
import com.saint.struct.StructApp
import com.saint.struct.adapter.HomeAdapter
import com.saint.struct.databinding.FragmentHomeBinding
import com.saint.struct.model.HomeItem
import com.saint.struct.repository.HomeRepository
import com.saint.struct.tool.MockUtils
import com.saint.struct.viewmodel.CordViewModel
import com.saint.struct.viewmodel.CordViewModelFactory
import com.saint.struct.viewmodel.HomeViewModel
import com.saint.struct.viewmodel.HomeViewModelFactory
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var refreshLayout: SmartRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initData() {

    }

    override fun <T : ViewModel> createViewModel(cls: Class<T>?): T {
        val repository: HomeRepository = object : HomeRepository {
            override suspend fun getHomeData(page: Int): List<HomeItem> {
                return MockUtils().mockData(page)
            }
        }
        return ViewModelProvider(
                this, HomeViewModelFactory(
                    repository,
                    activity?.application ?: StructApp.application
                )
            )[HomeViewModel::class.java] as T
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
    }

    fun initView() {
        recyclerView = binding.recyclerView
        refreshLayout = binding.refreshLayout
        progressBar = binding.progressBar
        initRecyclerView()
        initRefreshLayout()
    }

    private fun initRecyclerView() {
        homeAdapter = HomeAdapter(onItemClick = { item ->
            // 处理商品点击事件
            Log.e("HomeFragment", "item = ${item.id}")
        }, true)

        homeAdapter.apply {
            addLoadStateListener { loadStates ->
                when (loadStates.refresh) {
                    is LoadState.NotLoading -> {
                        if (homeAdapter.itemCount > 0) {
                            binding.recyclerView.scrollToPosition(0)
                        }
                        // 根据LoadState，结束smartRefreshLayout的对应状态
                        refreshLayout.finishRefresh()
                        refreshLayout.finishLoadMore()
                        progressBar.visibility = View.INVISIBLE
                        recyclerView.visibility = View.VISIBLE
                    }

                    is LoadState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.INVISIBLE
                    }

                    is LoadState.Error -> {
                        progressBar.visibility = View.INVISIBLE
                        val state = loadStates.refresh as LoadState.Error
                        Toast.makeText(
                            activity,
                            "Load Error: ${state.error.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.recyclerView.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.VERTICAL
                // 添加初始滚动位置控制
                setHasFixedSize(true)  // 添加性能优化参数
            }
        }
    }

    private fun initRefreshLayout() {
        refreshLayout.apply {
            setEnableRefresh(true)
            setEnableOverScrollBounce(true)
            setEnableOverScrollDrag(false)
            setDisableContentWhenRefresh(true);
            setReboundDuration(300)
            setOnRefreshListener {
                homeAdapter.refresh()
            }
//            setOnLoadMoreListener { refreshLayout ->
//                // 通过协程触发加载更多
//                lifecycleScope.launch {
//                    // 正确获取加载状态的方式
//                    val loadState = homeAdapter.loadStateFlow
//                    when (loadState) {
//                        is LoadState.NotLoading -> {
//                            if (homeAdapter.itemCount == 0) {
//                                refreshLayout.finishLoadMoreWithNoMoreData()
//                            } else {
//                                refreshLayout.finishLoadMore()
//                            }
//                        }
//
//                        is LoadState.Error -> {
//                            refreshLayout.finishLoadMore(false)
//                        }
//
//                        else -> {}
//                    }
//                }
//            }
        }
    }

    private fun observeViewModel() {
        Log.e("CoordinatorFragment", "Home = ")
        lifecycleScope.launch {
            Log.e("CoordinatorFragment", "Home lifecycleScope = ")
            viewModel.homeData.collectLatest { pagingData ->
                homeAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            homeAdapter.loadStateFlow.collect { loadState ->
                binding.refreshLayout.apply {
                    // 处理下拉刷新状态
                    if (loadState.refresh is LoadState.NotLoading) {
                        finishRefresh()
                    }
                    if (loadState.refresh is LoadState.Error) {
                        finishRefresh(false)
                    }

                    // 处理上拉加载状态
                    when (loadState.append) {
                        is LoadState.NotLoading -> {
                            if (homeAdapter.itemCount == 0) {
                                finishLoadMoreWithNoMoreData()
                            } else {
                                finishLoadMore()
                            }
                        }

                        is LoadState.Error -> finishLoadMore(false)
                        else -> Unit
                    }
                }
            }
        }
    }
}
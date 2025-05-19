package com.saint.struct.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.saint.struct.R
import com.saint.struct.adapter.HomeAdapter
import com.saint.struct.databinding.FragmentHomeBinding
import com.saint.struct.model.HomeItem
import com.saint.struct.repository.HomeRepository
import com.saint.struct.tool.MockUtils
import com.saint.struct.viewmodel.HomeViewModel
import com.saint.struct.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(
            object : HomeRepository {
                override suspend fun getHomeData(page: Int): List<HomeItem> {
                    // 这里实现具体的数据获取逻辑
//                    return apiService.fetchHomeData(page).data
                    return MockUtils().mockData(page)
                }
            })
    }
    private lateinit var homeAdapter: HomeAdapter


    override fun getLayoutId() = R.layout.fragment_home
    override fun doInit() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initRefreshLayout()
        observeViewModel()
    }

    private fun initRecyclerView() {
        homeAdapter = HomeAdapter { item ->
            // 处理商品点击事件
            Log.e("HomeFragment","item ${item.imageUrl}")
        }

//        homeAdapter.apply {
//            withLoadStateHeaderAndFooter(
//                header = headerAndFooterAdapter,
//                footer = headerAndFooterAdapter
//            )
//            LoadStates.append：适用于在用户当前位置之后获取的项的LoadState
//            LoadStates.prepend：适用于在用户当前位置之前获取的项的LoadState
//            LoadStates.refresh：适用于初始加载的LoadState
//            addLoadStateListener { combinedLoadStates ->
//                // 适用于初始加载的 LoadState
//                when (combinedLoadStates.refresh) {
//                    // 正在加载
//                    is LoadState.Loading -> {}
//                    // 加载错误
//                    is LoadState.Error -> {}
//                    // 未加载，无错误
//                    is LoadState.NotLoading -> {}
//                }
//            }
//        }

        (fragmentBinding as FragmentHomeBinding).recyclerView.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(context)
//            addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                }
//            })
        }
    }

    private fun initRefreshLayout() {
        (fragmentBinding as FragmentHomeBinding).refreshLayout.apply {
            setEnableOverScrollBounce(true) // 开启越界回弹
            setEnableOverScrollDrag(true) // 开启越界拖动
            setReboundDuration(300) // 设置回弹动画时长
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.homeData.collectLatest { pagingData ->
                homeAdapter.submitData(pagingData)  // 使用PagingDataAdapter的submitData方法
            }
        }
    }
}
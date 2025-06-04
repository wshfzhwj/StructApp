package com.saint.struct.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saint.struct.R
import com.saint.struct.adapter.CoordinatorAdapter
import com.saint.struct.adapter.SaintBannerImageAdapter
import com.saint.struct.databinding.FragmentCoordinatorBinding
import com.saint.struct.model.HomeItem
import com.saint.struct.repository.HomeRepository
import com.saint.struct.tool.MockUtils
import com.saint.struct.viewmodel.CordViewModel
import com.saint.struct.viewmodel.CordViewModelFactory
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CoordinatorFragment : BaseFragment() {
    private lateinit var cordAdapter: CoordinatorAdapter
    private lateinit var bannerAdapter: SaintBannerImageAdapter
    private lateinit var binding: FragmentCoordinatorBinding
    private val bannerItems = listOf(
        "https://fastly.picsum.photos/id/662/375/200.jpg?hmac=NTKu5GoJnCBC_0-esaeG3CAaRRsyuGc8xMgjtDvGeC8",
        "https://fastly.picsum.photos/id/553/375/200.jpg?hmac=W_W2fS4O2RKH6gKjvmMFXutuMAVAxR2vFo2D1z4kzco",
        "https://fastly.picsum.photos/id/190/375/200.jpg?hmac=Cl6YxbEYeSH_C1ogIcp0TchXds58uMgDo27UwVlfOCE"
    )

    private val viewModel: CordViewModel by viewModels {
        CordViewModelFactory(object : HomeRepository {
            override suspend fun getHomeData(page: Int): List<HomeItem> {
//              return apiService.fetchHomeData(page).data
                return MockUtils().mockData(page)
            }
        })
    }

    override fun initLayoutId() = R.layout.fragment_coordinator

    override fun initData() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = fragmentBinding as FragmentCoordinatorBinding
        initView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        cordAdapter = CoordinatorAdapter(onItemClick = { item ->
            // 处理商品点击事件
            Log.e("CoordinatorFragment", "item = ${item.id}")
        })
        setLoadStateListener()

        binding.recyclerView.apply {
            adapter = cordAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.VERTICAL
                // 添加初始滚动位置控制
                setHasFixedSize(true)  // 添加性能优化参数
            }
            setScrollListener()
        }
    }

    fun setLoadStateListener() {
        cordAdapter.addLoadStateListener { loadStates: CombinedLoadStates ->
            when {
                loadStates.source.refresh is LoadState.Loading -> {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.banner.visibility = View.VISIBLE
                }
                loadStates.source.refresh is LoadState.NotLoading -> {
                    if (cordAdapter.itemCount == 0) {
                        binding.recyclerView.visibility = View.GONE
                        binding.banner.visibility = View.GONE
                    } else {
                        // 确保RecyclerView可见
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.banner.visibility = View.VISIBLE
                    }
                }
                loadStates.append is LoadState.Loading -> {
                    // 显示底部加载进度条
                    binding.refreshLayout.finishLoadMoreWithNoMoreData()
                }
                loadStates.append is LoadState.Error -> {
                    Toast.makeText(requireContext(), "加载更多失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun setScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var totalDy = 0
            private val maxBannerHeight = resources.getDimensionPixelSize(R.dimen.banner_max_height)
            private val minBannerHeight = resources.getDimensionPixelSize(R.dimen.banner_min_height)

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.e("CoordinatorFragment", "onScrolled dy = $dy dx = $dx")
                totalDy += dy

                // 计算新的Banner高度
                val newHeight = (maxBannerHeight - totalDy.coerceAtLeast(0)).coerceIn(
                    minBannerHeight, maxBannerHeight
                )

                // 动态更新Banner高度
                binding.banner.layoutParams?.let {
                    it.height = newHeight
                    binding.banner.layoutParams = it
                }
            }
        })
    }

    private fun initRefreshLayout() {
        binding.refreshLayout.apply {
            setEnableRefresh(true)
            setEnableOverScrollBounce(true)
            setEnableOverScrollDrag(false)
            setDisableContentWhenRefresh(true);
            setReboundDuration(300)
            setOnRefreshListener {
                cordAdapter.refresh()
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.cordData.collectLatest { pagingData ->
                Log.e("CoordinatorFragment", "observeViewModel = $pagingData")
                cordAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            cordAdapter.loadStateFlow.collect { loadState ->
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
                            if (cordAdapter.itemCount == 0) {
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

    fun initBanner() {
        bannerAdapter = SaintBannerImageAdapter(bannerItems)
        binding.banner.apply {
            setAdapter(bannerAdapter)
            // 绑定Fragment生命周期
            addBannerLifecycleObserver(
                (context as? FragmentActivity)?.supportFragmentManager?.findFragmentById(R.id.nav_host)?.viewLifecycleOwner
                    ?: return
            )
            setBannerRound(8f)
            setLoopTime(3000)
            indicator = CircleIndicator(context)
        }
    }


    fun initView() {
        initBanner()
        initRecyclerView()
        initRefreshLayout()
    }
}
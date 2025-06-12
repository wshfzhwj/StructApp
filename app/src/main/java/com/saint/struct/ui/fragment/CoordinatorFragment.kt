package com.saint.struct.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.lifecycle.repeatOnLifecycle
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
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnMultiListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoordinatorFragment : BaseFragment() {
    private lateinit var cordAdapter: CoordinatorAdapter
    private lateinit var bannerAdapter: SaintBannerImageAdapter
    private lateinit var binding: FragmentCoordinatorBinding
    private lateinit var viewModel: CordViewModel
    private var items: MutableList<HomeItem> = mutableListOf()
    private var page: Int = 1
    private val bannerItems = listOf(
        "https://fastly.picsum.photos/id/662/375/200.jpg?hmac=NTKu5GoJnCBC_0-esaeG3CAaRRsyuGc8xMgjtDvGeC8",
        "https://fastly.picsum.photos/id/553/375/200.jpg?hmac=W_W2fS4O2RKH6gKjvmMFXutuMAVAxR2vFo2D1z4kzco",
        "https://fastly.picsum.photos/id/190/375/200.jpg?hmac=Cl6YxbEYeSH_C1ogIcp0TchXds58uMgDo27UwVlfOCE"
    )

    override fun initLayoutId() = R.layout.fragment_coordinator

    override fun initData() {
        val repository = object : HomeRepository {
            override suspend fun getHomeData(page: Int): List<HomeItem> {
                Log.e("CoordinatorFragment", "mockData page = $page")
                return MockUtils().mockData(page)
            }
        }
        viewModel =
            ViewModelProvider(this, CordViewModelFactory(repository))[CordViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = fragmentBinding as FragmentCoordinatorBinding
        initView()
        viewModel.getHomeData(1)
        observeViewModel()
    }

    private fun initRecyclerView() {
        cordAdapter = CoordinatorAdapter(items, onItemClick = { item ->
            // 处理商品点击事件
            Log.e("CoordinatorFragment", "item = ${item.id}")
        })

        binding.recyclerView.apply {
            adapter = cordAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.VERTICAL
                // 添加初始滚动位置控制
                setHasFixedSize(true)  // 添加性能优化参数
            }
        }
    }


    private fun initRefreshLayout() {
        binding.refreshLayout.apply {
            setEnableRefresh(true)
            setEnableOverScrollBounce(true)
            setEnableOverScrollDrag(false)
            setDisableContentWhenRefresh(true);
            setReboundDuration(300)
            setOnRefreshListener(object : OnRefreshListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onRefresh(refreshLayout: RefreshLayout) {
                    Log.e("CoordinatorFragment", "onRefresh")
                    page = 1
                    viewModel.getHomeData(1)
                    items.clear()
                    cordAdapter.notifyDataSetChanged()

                }

            })

            setOnLoadMoreListener(object : OnLoadMoreListener {
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    Log.e("CoordinatorFragment", "onLoadMore $page")
                    page++
                    viewModel.getHomeData(page)
                }

            })
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeViewModel() {
        Log.e("CoordinatorFragment", "Lifecycle.State.RESUMED")
        viewModel.items.observe(viewLifecycleOwner) { list ->
            lifecycleScope.launch {
                withContext(Dispatchers.Main) {
                    when {
                        binding.refreshLayout.isRefreshing -> {
                            items.clear()
                            items.addAll(list)
                            binding.refreshLayout.finishRefresh()
                            cordAdapter.notifyDataSetChanged()
                        }

                        binding.refreshLayout.isLoading -> {
                            var startPos = items.size
                            items.clear()
                            items.addAll(list)
                            cordAdapter.notifyItemRangeInserted(startPos, list.size)
                            cordAdapter.notifyItemRangeChanged(startPos, list.size)
                            binding.refreshLayout.finishLoadMore(true)
                        }

                        else -> {
                            items.addAll(list)
                            cordAdapter.notifyDataSetChanged()

                        }
                    }
//                Log.e("CoordinatorFragment", "observe list ${list.size}")
//                if (items.isEmpty()) {
//                    //第一次初始化
//                    Log.e("CoordinatorFragment", "observe else")
//                    items.addAll(list)
//                    cordAdapter.notifyItemRangeInserted(0, list.size)
//                    cordAdapter.notifyItemRangeChanged(0, list.size)
//                } else {
//                    var tempSize = items.size
//                    Log.e("CoordinatorFragment", "tempSize = $tempSize")
//                    if (binding.refreshLayout.isRefreshing) {
//                        Log.e("CoordinatorFragment", "observe isRefreshing")
//
//                    } else if (binding.refreshLayout.isLoading) {
//                        if (list.size > tempSize) {
//                            items.clear()
//                            items.addAll(list)
//                            binding.refreshLayout.finishLoadMore(true)
//                            cordAdapter.notifyItemRangeInserted(tempSize, list.size)
//                            cordAdapter.notifyItemRangeChanged(tempSize, list.size)
//                        } else {
//                            binding.refreshLayout.finishLoadMore(true)
//                        }
//                    }
//                }

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
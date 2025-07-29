package com.saint.struct.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoordinatorFragment : BaseFragment<FragmentCoordinatorBinding>() {
    private lateinit var cordAdapter: CoordinatorAdapter

    //    private lateinit var bannerAdapter: SaintBannerImageAdapter
    private lateinit var viewModel: CordViewModel
    private var page: Int = 1
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCoordinatorBinding {
        return FragmentCoordinatorBinding.inflate(inflater, container, false)
    }
//    private val bannerItems = listOf(
//        "https://fastly.picsum.photos/id/662/375/200.jpg?hmac=NTKu5GoJnCBC_0-esaeG3CAaRRsyuGc8xMgjtDvGeC8",
//        "https://fastly.picsum.photos/id/553/375/200.jpg?hmac=W_W2fS4O2RKH6gKjvmMFXutuMAVAxR2vFo2D1z4kzco",
//        "https://fastly.picsum.photos/id/190/375/200.jpg?hmac=Cl6YxbEYeSH_C1ogIcp0TchXds58uMgDo27UwVlfOCE"
//    )


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
        initView()
        Log.e("CoordinatorFragment", "getHomeData onViewCreated")
        viewModel.getHomeData(1)
        observeViewModel()
    }

    private fun initRecyclerView() {
        cordAdapter = CoordinatorAdapter(onItemClick = { item ->
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


//    private fun initRefreshLayout() {
//        binding.refreshLayout.apply {
//            setEnableRefresh(true)
//            setEnableOverScrollBounce(true)
//            setEnableOverScrollDrag(false)
//            setDisableContentWhenRefresh(true);
//            setReboundDuration(300)
//            setOnRefreshListener(object : OnRefreshListener {
//                override fun onRefresh(refreshLayout: RefreshLayout) {
//                    Log.e("CoordinatorFragment", "getHomeData onRefresh")
//                    page = 1
//                    viewModel.getHomeData(1)
//                }
//
//            })
//
//            setOnLoadMoreListener(object : OnLoadMoreListener {
//                override fun onLoadMore(refreshLayout: RefreshLayout) {
//                    page++
//                    Log.e("CoordinatorFragment", "getHomeData onLoadMore page = $page")
//                    viewModel.getHomeData(page)
//                }
//
//            })
//        }
//    }

//    @SuppressLint("NotifyDataSetChanged")
//    private fun observeViewModel() {
//        viewModel.items.observe(viewLifecycleOwner) { list ->
//            lifecycleScope.launch {
//                Log.e("CoordinatorFragment", "observeViewModel = ${list.size}")
//                withContext(Dispatchers.Main) {
//                    when {
//                        binding.refreshLayout.isRefreshing -> {
//                            cordAdapter.setData(list)
//                            binding.refreshLayout.finishRefresh()
//                        }
//
//                        binding.refreshLayout.isLoading -> {
//                            if (list.isEmpty()) {
//                                page--
//                            }
//                            cordAdapter.addAll(list)
//                            binding.refreshLayout.finishLoadMore(true)
//
//                        }
//
//                        else -> {
//                            cordAdapter.setData(list)
//                        }
//                    }
//                }
//            }
//        }
//    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeViewModel() {
        viewModel.items.observe(viewLifecycleOwner) { list ->
            lifecycleScope.launch {
                Log.e("CoordinatorFragment", "observeViewModel = ${list.size}")
                withContext(Dispatchers.Main) {
                    if (list.isEmpty()) {
                        page--
                    }
                    if (cordAdapter.itemCount == 0) {
                        cordAdapter.setData(list)
                    } else {
                        cordAdapter.addAll(list)
                    }
                }
            }
        }
    }

//    fun initBanner() {
//        bannerAdapter = SaintBannerImageAdapter(bannerItems)
//        binding.banner.apply {
//            setAdapter(bannerAdapter)
//            // 绑定Fragment生命周期
//            addBannerLifecycleObserver(
//                (context as? FragmentActivity)?.supportFragmentManager?.findFragmentById(R.id.nav_host)?.viewLifecycleOwner
//                    ?: return
//            )
//            setBannerRound(8f)
//            setLoopTime(3000)
//            indicator = CircleIndicator(context)
//        }
//    }


    fun initView() {
//        initBanner()
        initRecyclerView()
//        initRefreshLayout()
    }
}
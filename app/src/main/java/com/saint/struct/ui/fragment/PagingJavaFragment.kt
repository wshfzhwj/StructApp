package com.saint.struct.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saint.struct.R
import com.saint.struct.adapter.PagingJavaAdapter
import com.saint.struct.bean.WanListBean
import com.saint.struct.databinding.FragmentPagingBinding
import com.saint.struct.viewmodel.PageOldViewModel

private const val TAG = "PageOldFragment"

class PagingJavaFragment : BaseFragment() {
    private lateinit var pageAdapter: PagingJavaAdapter
//    private lateinit var mViewModel: PageOldViewModel

    override fun getFragmentLayoutId(): Int {
        return R.layout.fragment_paging
    }

    override fun doInit() {
        initTitle()
        initRecyclerView()
        setModelAndData()
        setListener()
    }

    fun initTitle() {
        (fragmentBinding as FragmentPagingBinding).layoutAppBar.titleBar.text = "PagingJavaFragment"
    }

    private fun setListener() {
    }

    private fun initRecyclerView() {
        pageAdapter = PagingJavaAdapter()
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        (fragmentBinding as FragmentPagingBinding).recyclerView.apply {
            this.layoutManager = layoutManager
            this.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            this.adapter = pageAdapter
        }

        setModelAndData()
    }

    fun setModelAndData() {
//        val mViewModelProvider = ViewModelProvider(
//            this,
//            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
//        )
//        mViewModel = mViewModelProvider[PageOldViewModel::class.java]
        val factory = ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        val mViewModel: PageOldViewModel by viewModels { factory }
        mViewModel.pagedList.observe(requireActivity()) { dataBeans: PagedList<WanListBean> -> pageAdapter.submitList(dataBeans) }
    }
}
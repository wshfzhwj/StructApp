package com.saint.struct.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saint.struct.adapter.PagingArticleAdapter
import com.saint.struct.databinding.FragmentPagingBinding
import com.saint.struct.viewmodel.PageArticleViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PagingJavaFragment : BaseFragment<FragmentPagingBinding, PageArticleViewModel>() {
    private lateinit var pageAdapter: PagingArticleAdapter
    override val viewModel: PageArticleViewModel by viewModels()

    //    private lateinit var mViewModel: PageOldViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitle()
        initRecyclerView()
        setModelAndData()
    }

    override fun getViewBinding(): FragmentPagingBinding {
        return FragmentPagingBinding.inflate(layoutInflater)
    }

    override fun initData() {
    }

    fun initTitle() {
        binding.layoutAppBar.titleBar.text = "PagingJavaFragment"
    }

    private fun initRecyclerView() {
        pageAdapter = PagingArticleAdapter()
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.apply {
            this.layoutManager = layoutManager
            this.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            this.adapter = pageAdapter
        }
    }

    fun setModelAndData() {
//        val mViewModelProvider = ViewModelProvider(
//            this,
//            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
//        )
//        mViewModel = mViewModelProvider[PageOldViewModel::class.java]
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.articles.collectLatest {
                    pageAdapter.submitData(lifecycle, it)
                }
            }
        }
    }
}
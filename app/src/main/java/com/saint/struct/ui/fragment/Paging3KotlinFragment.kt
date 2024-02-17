package com.saint.struct.ui.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import com.saint.struct.adapter.Paging3KotlinAdapter
import com.saint.struct.bean.Project
import com.saint.struct.databinding.FragmentPagingBinding
import com.saint.struct.viewmodel.Page3KtViewModel
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "Paging3KotlinFragment"

class Paging3KotlinFragment : BasePagingFragment<Project, Paging3KotlinAdapter.ViewHolder>() {

    private val viewModel: Page3KtViewModel by viewModels()
//    val viewModel :ArticleViewModel by viewModels()

    override fun setModelAndData() {
        lifecycleScope.launchWhenCreated {
            viewModel.projects.collectLatest {
                pageAdapter.submitData(lifecycle, it)
            }
        }
    }

    override fun getCustomPageAdapter(): PagingDataAdapter<Project, Paging3KotlinAdapter.ViewHolder> {
        return Paging3KotlinAdapter(requireActivity())
    }

    override fun initTitle() {
        (fragmentBinding as FragmentPagingBinding).layoutAppBar.titleBar.text = "Paging3KotlinFragment"
    }
}
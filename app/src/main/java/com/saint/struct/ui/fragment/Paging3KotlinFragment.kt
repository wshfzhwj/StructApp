package com.saint.struct.ui.fragment

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingDataAdapter
import com.saint.struct.adapter.PagingProjectAdapter
import com.saint.struct.bean.Project
import com.saint.struct.databinding.FragmentPagingBinding
import com.saint.struct.viewmodel.PageProjectViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "Paging3KotlinFragment"

class Paging3KotlinFragment : BasePagingFragment<Project, PagingProjectAdapter.ViewHolder>() {

    private val viewModel: PageProjectViewModel by viewModels()
//    val viewModel :ArticleViewModel by viewModels()

    override fun setModelAndData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.projects.collectLatest {
                    pageAdapter.submitData(lifecycle, it)
                }
            }
        }
    }

    override fun setListener() {}

    override fun getCustomPageAdapter(): PagingDataAdapter<Project, PagingProjectAdapter.ViewHolder> {
        return PagingProjectAdapter(requireActivity())
    }

    @SuppressLint("SetTextI18n")
    override fun initTitle() {
        (fragmentBinding as FragmentPagingBinding).layoutAppBar.titleBar.text = "Paging3KotlinFragment"
    }
}
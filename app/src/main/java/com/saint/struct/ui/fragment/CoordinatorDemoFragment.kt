package com.saint.struct.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.saint.struct.databinding.FragmentCoordDemoBinding

class CoordinatorDemoFragment: BaseFragment<FragmentCoordDemoBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCoordDemoBinding {
        return FragmentCoordDemoBinding.inflate(inflater,container,false)
    }

    override fun initData() {
    }
}
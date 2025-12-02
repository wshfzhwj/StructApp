package com.saint.struct.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.saint.struct.viewmodel.BaseViewModel

/**
 * Fragment的基类，利用泛型和属性委托简化ViewBinding和ViewModel的初始化。
 *
 * @param VB ViewBinding的具体类型
 * @param VM ViewModel的具体类型
 * @author wangsf
 */
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {
    private var _binding: VB? = null
    // 此属性只在 onCreateView 和 onDestroyView 之间有效。
    protected val binding get() = _binding!!

    // 使用属性委托 'by viewModels()' 来声明ViewModel，具体实例由子类提供。
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        observeViewModel()
    }

    protected abstract fun getViewBinding(): VB

    /**
     * 子类必须实现此方法来初始化数据和视图。
     * 在 onViewCreated 中调用，此时视图已创建完毕。
     */
    abstract fun initData()

    /**
     * 可选的覆写方法，用于集中观察ViewModel中的LiveData。
     */
    protected open fun observeViewModel() {
        // 子类可以覆写此方法来统一处理对ViewModel的观察
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 释放绑定，防止内存泄漏
        _binding = null
    }
}

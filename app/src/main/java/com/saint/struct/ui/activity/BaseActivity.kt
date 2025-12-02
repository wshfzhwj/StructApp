package com.saint.struct.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.saint.struct.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<T : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    // _binding 保持不变，是私有的，防止外部修改
    private var _binding: T? = null
    // binding 是公开的只读属性，且在 onDestroyView 中会置空，避免内存泄漏
    protected val binding get() = _binding!!

    // lateinit 配合 by lazy 可以更安全
    protected lateinit var viewModel: VM
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 初始化 ViewModel
        viewModel = createViewModelInstance()

        // 初始化 ViewBinding
        _binding = getViewBinding()
        setContentView(binding.root)
    }

    private fun createViewModelInstance(): VM {
        val viewModelClass = (javaClass.genericSuperclass as? ParameterizedType)
            ?.actualTypeArguments
            ?.getOrNull(1) as? Class<VM>
            ?: throw IllegalArgumentException("ViewModel class not found in generic type arguments of $this")

        return ViewModelProvider(this)[viewModelClass]
    }

    override fun onDestroy() {
        super.onDestroy()
        // 关键一步：在 Activity 销毁时解除对 binding 的引用，防止内存泄漏
        _binding = null
    }

    protected abstract fun getViewBinding(): T
}

package com.saint.struct.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.saint.struct.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<T : ViewBinding, VM: BaseViewModel> : Fragment() {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    protected lateinit var viewModel: VM


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = getViewBinding(inflater,container)
        initBase()
        initData()
        return binding.root
    }

    private fun initBase(){
        //获取ViewModel类型
        val modelClass: Class<BaseViewModel>
        //获取带有泛型的父类
        val type = javaClass.genericSuperclass
        //ParameterizedType参数化类型，即泛型
        modelClass = if (type is ParameterizedType) {
            //getActualTypeArguments获取参数化类型的数组，泛型可能有多个，这里我们默认第二个泛型是ViewModel
            type.actualTypeArguments[1] as Class<BaseViewModel>
        } else {
            //如果没有指定泛型参数，则默认使用ViewModel
            BaseViewModel::class.java
        }
        //初始化viewModel
        viewModel = createViewModel(modelClass as Class<VM>)
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): T
    abstract fun initData()

    /**
     * 创建ViewModel 如果 需要自己定义ViewModel 直接复写此方法
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    open fun <T : ViewModel> createViewModel(cls: Class<T>?): T {
        return ViewModelProvider(this)[cls!!]
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
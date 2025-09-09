package com.saint.struct.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.saint.struct.tool.StatusBarUtils
import com.saint.struct.viewmodel.BaseViewModel
import com.saint.struct.viewmodel.HomeViewModel
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<T: ViewBinding,VM: BaseViewModel> : AppCompatActivity() {

    private lateinit var _binding: T
    protected val binding get() = _binding;


    private lateinit var _viewModel: VM
    protected val viewModel get() = _viewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBase()
        _binding = getViewBinding()
        setContentView(_binding.root)
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
        _viewModel = createViewModel(this, modelClass as Class<VM>)
    }

    /**
     * 创建ViewModel 如果 需要自己定义ViewModel 直接复写此方法
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    open fun <T : ViewModel> createViewModel(activity: FragmentActivity?, cls: Class<T>?): T {
        return ViewModelProvider(activity!!)[cls!!]
    }

    protected abstract fun getViewBinding(): T
}
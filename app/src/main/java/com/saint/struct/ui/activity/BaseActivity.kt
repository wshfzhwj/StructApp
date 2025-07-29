package com.saint.struct.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.saint.struct.tool.StatusBarUtils

abstract class BaseActivity<T: ViewBinding> : AppCompatActivity() {

    private lateinit var _binding: T
    protected val binding get() = _binding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(_binding.root)
    }


    protected abstract fun getViewBinding(): T
}
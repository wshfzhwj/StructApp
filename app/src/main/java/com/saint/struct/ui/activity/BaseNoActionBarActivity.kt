package com.saint.struct.ui.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.saint.struct.tool.StatusBarUtils
import com.saint.struct.viewmodel.BaseViewModel

abstract class BaseNoActionBarActivity<T: ViewBinding,VM: BaseViewModel> : BaseActivity<T,VM>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideActionBar()
        StatusBarUtils.setDeepStatusBar(true, this)
    }

    fun hideActionBar() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }
}
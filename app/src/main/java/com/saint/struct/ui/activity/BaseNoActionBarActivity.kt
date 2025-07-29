package com.saint.struct.ui.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.saint.struct.tool.StatusBarUtils

abstract class BaseNoActionBarActivity<T: ViewBinding> : BaseActivity<T>() {
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
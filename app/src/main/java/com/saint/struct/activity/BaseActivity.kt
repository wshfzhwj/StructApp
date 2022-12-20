package com.saint.struct.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saint.struct.tool.StatusBarUtils

open class BaseActivity : AppCompatActivity() {
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
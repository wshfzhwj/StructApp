package com.saint.struct.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saint.struct.tool.StatusBarUtils

open class BaseNoActionBarActivity : BaseActivity() {
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
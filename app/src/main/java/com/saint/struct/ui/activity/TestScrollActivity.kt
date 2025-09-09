package com.saint.struct.ui.activity

import android.os.Bundle
import com.saint.struct.R
import com.saint.struct.databinding.ActivityScrollBinding
import com.saint.struct.viewmodel.BaseViewModel
import com.saint.struct.viewmodel.TestScrollViewModel

class TestScrollActivity : BaseActivity<ActivityScrollBinding, TestScrollViewModel>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getViewBinding(): ActivityScrollBinding {
        return ActivityScrollBinding.inflate(layoutInflater)
    }
}
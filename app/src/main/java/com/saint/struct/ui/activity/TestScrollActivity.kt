package com.saint.struct.ui.activity

import android.os.Bundle
import com.saint.struct.R
import com.saint.struct.databinding.ActivityScrollBinding

class TestScrollActivity : BaseActivity<ActivityScrollBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(ActivityCoordTest4Binding.inflate(layoutInflater).root)
    }

    override fun getViewBinding(): ActivityScrollBinding {
        return ActivityScrollBinding.inflate(layoutInflater)
    }
}
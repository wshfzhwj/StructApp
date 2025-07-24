package com.saint.struct.ui.activity

import android.os.Bundle
import com.saint.struct.R

class TestScrollActivity: BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(ActivityCoordTest4Binding.inflate(layoutInflater).root)
        setContentView(R.layout.activity_scroll)
    }
}
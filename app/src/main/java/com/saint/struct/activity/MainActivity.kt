package com.saint.struct.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.saint.struct.R
import com.saint.struct.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var mLayoutBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLayoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val fragmentContainerView = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val bottom = mLayoutBinding.navBottom
        val navController = fragmentContainerView.navController
        bottom.setupWithNavController(navController)
    }

}


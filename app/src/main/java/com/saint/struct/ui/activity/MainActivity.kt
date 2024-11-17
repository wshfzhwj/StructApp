package com.saint.struct.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.saint.struct.R
import com.saint.struct.databinding.ActivityMainBinding
import com.saint.struct.viewmodel.MainActivityViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : BaseActivity() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
//    var viewModel: MainActivityViewModel by lazy { ViewModelProvider(this).get(MainActivityViewModel::class)}
    private lateinit var mLayoutBinding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLayoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val fragmentContainerView = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val bottom = mLayoutBinding.navBottom
        val navController = fragmentContainerView.navController
        bottom.setupWithNavController(navController)


//        lifecycleScope.launch{
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                mainActivityViewModel.navigationActions().collect{
//                    println()
//                }
//            }
//        }
    }
}



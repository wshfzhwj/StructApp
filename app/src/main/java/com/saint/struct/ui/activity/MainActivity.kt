package com.saint.struct.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.saint.struct.R
import com.saint.struct.databinding.ActivityMainBinding
import com.saint.struct.viewmodel.MainActivityViewModel

class MainActivity: BaseActivity<ActivityMainBinding>() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    //    var viewModel: MainActivityViewModel by lazy { ViewModelProvider(this).get(MainActivityViewModel::class)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentContainerView = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val bottom = binding.navBottom
        val navController = fragmentContainerView.navController
        bottom.setupWithNavController(navController)


//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.CREATED) {
//                Log.d("MainActivity", "CREATED")
//
//            }
//        }
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                Log.d("MainActivity", "STARTED")
//            }
//
//        }
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.RESUMED) {
//                Log.d("MainActivity", "RESUMED")
//            }
//        }
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}



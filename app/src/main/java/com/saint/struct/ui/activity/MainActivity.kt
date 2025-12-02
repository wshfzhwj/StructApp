package com.saint.struct.ui.activity

import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.saint.struct.R
import com.saint.struct.databinding.ActivityMainBinding
import com.saint.struct.tool.TAG
import com.saint.struct.viewmodel.MainActivityViewModel
import kotlinx.coroutines.launch

class MainActivity: BaseActivity<ActivityMainBinding, MainActivityViewModel>() {
//    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    //    var viewModel: MainActivityViewModel by lazy { ViewModelProvider(this).get(MainActivityViewModel::class)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentContainerView = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val bottom = binding.navBottom
        val navController = fragmentContainerView.navController
        bottom.setupWithNavController(navController)
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.RESUMED) {
//                Log.d("MainActivity", "RESUMED")
//            }
//        }
    }
    /**
     * Returns the ViewBinding for this activity.
     *
     * This method is used to inflate the layout for this activity and return
     * the corresponding ViewBinding.
     *
     * @return The ViewBinding for this activity
     */
    override fun getViewBinding(): ActivityMainBinding {
        // Inflate the layout with the layout inflater and return the binding
        // This method is used by the BaseActivity class to set up the ViewBinding for
        // the activity.
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e(TAG, "onSaveInstanceState: ")
    }
}



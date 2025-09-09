package com.saint.struct.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saint.struct.repository.HomeRepository

class HomeViewModelFactory(
    private val repository: HomeRepository,val application: Application
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository ,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
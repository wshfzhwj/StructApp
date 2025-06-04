package com.saint.struct.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saint.struct.repository.HomeRepository

class CordViewModelFactory(
    private val repository: HomeRepository
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CordViewModel::class.java)) {
            return CordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
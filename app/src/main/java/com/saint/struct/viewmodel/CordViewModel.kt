package com.saint.struct.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saint.struct.model.HomeItem
import com.saint.struct.repository.HomeRepository
import kotlinx.coroutines.launch

class CordViewModel(val repository: HomeRepository) : ViewModel() {
    private var _items = MutableLiveData<MutableList<HomeItem>>()
    val items: LiveData<MutableList<HomeItem>> = _items

    fun getHomeData(page: Int) {
        viewModelScope.launch {
            var list = _items.value ?: mutableListOf()
            list.addAll(repository.getHomeData(page))
            _items.value = list
        }
    }
}
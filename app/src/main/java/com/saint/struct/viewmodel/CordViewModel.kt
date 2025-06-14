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
    private var _items: MutableLiveData<MutableList<HomeItem>> = MutableLiveData()
    val items: LiveData<MutableList<HomeItem>> get() = _items

    fun getHomeData(page: Int) {
        viewModelScope.launch {
            Log.e("CoordinatorFragment", "getHomeData page = $page")
            var list = mutableListOf<HomeItem>()
            list.addAll(repository.getHomeData(page))
            _items.value = list
        }
    }
}

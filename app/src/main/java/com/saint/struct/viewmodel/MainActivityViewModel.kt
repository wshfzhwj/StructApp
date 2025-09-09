package com.saint.struct.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn

class MainActivityViewModel(
    application: Application
) : BaseViewModel(application) {

    fun navigationActions(): SharedFlow<Int> {
        return flow<Int> { 1..3 }.flowOn(Dispatchers.Default)
            .shareIn(viewModelScope, SharingStarted.Lazily, 100)
    }

}
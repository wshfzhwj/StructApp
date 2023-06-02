package com.saint.struct.kotlin.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saint.struct.data.remote.LoginRepository
import com.saint.struct.viewmodel.LoginViewModel
import kotlinx.coroutines.*

class CoroutinesTest {
    private val loginRepository = LoginRepository()
    private val loginViewModel = LoginViewModel(loginRepository)

    fun loginByContext() {
        loginViewModel.loginByMainThread("", "")
    }

    fun loginByDispatchersIo() {
        loginViewModel.loginByDispatchersIo("", "")
    }

    suspend fun loginBySafeMode() {
        loginViewModel.loginBySafeMode("", "")
    }

}

fun main() {
    val coroutinesTest = CoroutinesTest()
    coroutinesTest.loginByDispatchersIo()
    coroutinesTest.loginByContext()
//    This is a delicate API and its use requires care. Make sure you fully read and understand documentation of the declaration that is marked as a delicate API.
//    GlobalScope.launch() {
//        coroutinesTest.loginBySafeMode()
//    }


}
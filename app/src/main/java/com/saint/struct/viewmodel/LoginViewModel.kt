package com.saint.struct.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saint.struct.bean.Resource
import com.saint.struct.bean.User
import com.saint.struct.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {
    fun loginByMainThread(username: String, token: String) {
        // Create a new coroutine on the UI thread
        viewModelScope.launch {
            val jsonBody = "{ username: \"$username\", token: \"$token\"}"
            // Make the network call and suspend execution until it finishes
            val result = loginRepository.makeLoginRequest(jsonBody)
            // Display result of the network request to the user
            when (result) {
                is Resource.Success<User> -> println("aaa")// Happy path
                else -> Resource.DataError<User>("error", 100)// Show error in UI
            }
        }
    }

    fun loginByDispatchersIo(username: String, token: String) {
        // Create a new coroutine to move the execution off the UI thread
        viewModelScope.launch(Dispatchers.IO) {
            val jsonBody = "{ username: \"$username\", token: \"$token\"}"
            loginRepository.makeLoginRequest(jsonBody)
        }
    }

    suspend fun loginBySafeMode(username: String, token: String) {
        println("$username         $token")
        loginRepository.makeLoginSafeRequest("")
    }
}
package com.saint.struct.repository

import com.saint.kotlin.test.ktl.User
import com.saint.struct.bean.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository {
    fun makeLoginRequest(jsonBody: String): Resource<User> {
        println(jsonBody)
        return Resource.Success(User("", ""))
    }

    suspend fun makeLoginSafeRequest(jsonBody: String): Resource<User> {
        return withContext(Dispatchers.IO) {
            // Blocking network request code
            println(jsonBody)
            Resource.Success(User("", ""))
        }
    }
}
package com.saint.struct.data.remote

import com.saint.struct.bean.User
import com.saint.struct.bean.response.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository {
    fun makeLoginRequest(jsonBody: String): Resource<User> {
        println(jsonBody)
        return Resource.Success<User>(User("", ""))
    }

    suspend fun makeLoginSafeRequest(jsonBody: String): Resource<User> {
        return withContext(Dispatchers.IO) {
            // Blocking network request code
            println(jsonBody)
            Resource.Success<User>(User("", ""))
        }
    }
}
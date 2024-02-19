package com.saint.struct.bean

sealed class Resource<T>(val data: T? = null, private val errorMsg: String? = null, private val errorCode: Int? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class DataError<T>(errorMsg: String, errorCode: Int) : Resource<T>(null, errorMsg, errorCode)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is DataError -> "Error[exception=$errorCode$errorMsg]"
            is Loading<T> -> "Loading"
        }
    }
}

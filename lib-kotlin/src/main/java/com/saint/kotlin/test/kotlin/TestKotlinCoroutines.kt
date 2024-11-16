package com.saint.kotlin.test.kotlin

import kotlin.Result
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine

fun main() {
    suspend {
        1
    }.startCoroutine(object : Continuation<Int>{
        override val context = LogInterceptor()
        override fun resumeWith(result: Result<Int>) {
            println("result = $result")
        }
    })
}

class LogInterceptor : ContinuationInterceptor {
    override val key = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>) = LogContinuation(continuation)
}

class LogContinuation<T>(private val continuation: Continuation<T>) : Continuation<T> by continuation {
    override fun resumeWith(result: Result<T>) {
        println("before resumeWith")
        continuation.resumeWith(result)
        println("after resumeWith")
    }
}
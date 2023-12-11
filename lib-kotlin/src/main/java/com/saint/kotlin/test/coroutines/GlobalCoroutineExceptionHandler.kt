package com.saint.kotlin.test.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

class GlobalCoroutineExceptionHandler(
    val block: (context: CoroutineContext, exception: Throwable) -> Unit
) : CoroutineExceptionHandler {
    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        block(context, exception)
    }
}
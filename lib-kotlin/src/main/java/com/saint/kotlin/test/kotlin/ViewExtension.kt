package com.saint.kotlin.test.kotlin

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.experimental.ExperimentalTypeInference

fun View.forEach(action: View.(Int) -> Unit) = forEach(0, action)

private fun View.forEach(level: Int = 0, action: View.(Int) -> Unit) {
    this.action(level)
    if (this is ViewGroup) {
        this.children.forEach { it.forEach(level + 1, action) }
    }
}

fun ViewGroup.removeView(id: Int) {
    val v = findViewById(id) ?: return
    this.removeView(v)
}

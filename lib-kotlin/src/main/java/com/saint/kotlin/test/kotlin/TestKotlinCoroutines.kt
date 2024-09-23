package com.saint.kotlin.test.kotlin

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


suspend fun main() = runBlocking { // this: CoroutineScope
    launch {
        println("Task from runBlocking launch1 ")
    }
    println("Task from main")
}
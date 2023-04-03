package com.saint.struct.kotlin.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.Executors

class FlowTest {
    suspend fun testFlowOf() {
        flowOf(1, 2, 3, 4, 5)
            .onEach {
                delay(100)
            }
            .collect {
                println(it)
            }
    }

    suspend fun testAsFlow() {
        listOf(1, 2, 3, 4, 5).asFlow()
            .onEach {
                delay(100)
            }.collect {
                println(it)
            }
    }

    suspend fun testChannelFlow() = channelFlow {
        for (i in 1..5) {
            delay(100)
            send(i)
        }
    }.collect {
        println(it)
    }


    fun test() = runBlocking {
        val myDispatcher = Executors.newSingleThreadExecutor()
            .asCoroutineDispatcher()
        flow {
            println("emit on ${Thread.currentThread().name}")
            emit("data")
        }.map {
            println("run first map on${Thread.currentThread().name}")
            "$it map"
        }
            //作用于前面flow创建与第一个map
            .flowOn(Dispatchers.IO)
            .map {
                println("run second map on ${Thread.currentThread().name}")
                "${it},${it.length}"
            }
            //作用于第二个map
            .flowOn(myDispatcher)
            .collect {
                println("result $it on ${Thread.currentThread().name}")
            }
    }

    fun zip() {
        runBlocking {
            val flow = flowOf(1, 2, 3).onEach { delay(1000) }
            val flow2 = flowOf("a", "b", "c", "d").onEach { delay(15) }
            flow.zip(flow2) { i, s -> i.toString() + s }.collect {
                println(it) // Will print "1a 2b 3c"
            }
        }
    }
}

fun main() {
    val flowTest = FlowTest()
//    val scope = CoroutineScope(SupervisorJob())
//    scope.launch{
//        flowTest.testAsFlow()
//        flowTest.testFlowOf()
//        flowTest.testChannelFlow()
//    }

//    flowTest.test()
    flowTest.zip()
}
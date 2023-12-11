package com.saint.kotlin.test.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

/**
 * 流构建器
 *      flowOf 构建器定义了一个发射固定值集的流。
 *      使用 .asFlow() 扩展函数，可以将各种集合与序列转换为流
 * 过渡流操作符 map 与 filter
 * 转换操作符 transform
 * 末端流操作符
 *      转化为各种集合，例如 toList 与 toSet。
 *      获取第一个（first）值与确保流发射单个（single）值的操作符。
 *      使用 reduce 与 fold 将流规约到单个值。
 */
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

//fun main() {
//    val flowTest = FlowTest()
////    val scope = CoroutineScope(SupervisorJob())
////    scope.launch{
////        flowTest.testAsFlow()
////        flowTest.testFlowOf()
////        flowTest.testChannelFlow()
////    }
//
////    flowTest.test()
//    flowTest.zip()
//}

//sampleStart
//fun simple(): Flow<Int> = flow { // 流构建器
//    for (i in 1..3) {
//        delay(100) // 假装我们在这里做了一些有用的事情
//        emit(i) // 发送下一个值
//    }
//}
//
//fun main() = runBlocking<Unit> {
//    // 启动并发的协程以验证主线程并未阻塞
//    launch {
//        for (k in 1..3) {
//            println("I'm not blocked $k")
//            delay(100)
//        }
//    }
//    // 收集这个流
//    simple().collect { value -> println(value) }
//}
//sampleEnd

//sampleStart
//fun main() = runBlocking<Unit> {
//    val sum = (1..5).asFlow()
//        .map { it * it } // 数字 1 至 5 的平方
//        .reduce { a, b -> a + b } // 求和（末端操作符）
//    println(sum)
//}
//sampleEnd

//sampleStart
//fun simple(): Flow<Int> = flow {
//    for (i in 1..3) {
//        Thread.sleep(100) // 假装我们以消耗 CPU 的方式进行计算
//        log("Emitting $i")
//        emit(i) // 发射下一个值
//    }
//}.flowOn(Dispatchers.Default) // 在流构建器中改变消耗 CPU 代码上下文的正确方式
//
//fun main() = runBlocking<Unit> {
//    simple().collect { value ->
//        log("Collected $value")
//    }
//}
//sampleEnd

fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100) // 假装我们异步等待了 100 毫秒
        emit(i) // 发射下一个值
    }
}

//sampleStart
//fun main() = runBlocking<Unit> {
//    val time = measureTimeMillis {
//        simple()
//            .buffer() // 缓冲发射项，无需等待
//            .collect { value ->
//                delay(300) // 假装我们花费 300 毫秒来处理它
//                println(value)
//            }
//    }
//    println("Collected in $time ms")
//}
//sampleEnd


fun main() = runBlocking{
//sampleStart
    val time = measureTimeMillis {
        simple()
            .conflate() // 合并发射项，不对每个值进行处理
            .collect { value ->
                delay(300) // 假装我们花费 300 毫秒来处理它
                println(value)
            }
    }
    println("Collected in $time ms")
//sampleEnd
}

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

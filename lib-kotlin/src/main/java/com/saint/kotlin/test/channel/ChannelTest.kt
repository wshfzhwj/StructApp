package com.saint.kotlin.test.channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ChannelTest {
}

//sampleStart
//fun main() = runBlocking {
//    val channel = Channel<Int>()
//    launch {
//        // 这里可能是消耗大量 CPU 运算的异步逻辑，我们将仅仅做 5 次整数的平方并发送
//        for (x in 1..5) channel.send(x * x)
//    }
//    // 这里我们打印了 5 次被接收的整数：
//    repeat(5) { println(channel.receive()) }
//    println("Done!")
//}
//sampleEnd

//sampleStart
//fun main() = runBlocking {
//    val channel = Channel<Int>()
//    launch {
//        for (x in 1..5) channel.send(x * x)
//        channel.close() // 我们结束发送
//    }
//    // 这里我们使用 `for` 循环来打印所有被接收到的元素（直到通道被关闭）
//    for (y in channel) println(y)
//    println("Done!")
//}
//sampleEnd


suspend fun testChannelFlow() = channelFlow {
    for (i in 1..5) {
        delay(100)
        send(i)
    }
}.collect {
    println(it)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) send(x * x)
}

//sampleStart
//fun main() = runBlocking {
//    val squares = produceSquares()
//    squares.consumeEach { println(it) }
//    println("Done!")
//}
//sampleEnd

//sampleStart
//fun main() = runBlocking {
//    val numbers = produceNumbers() // 从 1 开始生成整数
//    val squares = square(numbers) // 整数求平方
//    repeat(5) {
//        println(squares.receive()) // 输出前五个
//    }
//    println("Done!") // 至此已完成
//    coroutineContext.cancelChildren() // 取消子协程
//}
//sampleEnd

//sampleStart
//fun main(): Unit = runBlocking {
//    val channel = Channel<Int>()
//
//    launch {
//        for (i in 1..5) {
//            delay(1000)
//            channel.send(i)
//        }
//        channel.close()
//    }
//
//    launch {
//        for (value in channel) {
//            println(value)
//        }
//    }
//}
//sampleEnd
@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.produceNumbers() = produce<Int> {
    var x = 1
    while (true) send(x++) // 从 1 开始的无限的整数流
}

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in numbers) send(x * x)
}


fun main() = runBlocking<Unit> {

    val tickerChannel = ticker(delayMillis = 1000, initialDelayMillis = 0)

    // 每秒打印
    for (unit in tickerChannel) {
        System.err.println("unit = $unit")
    }
}

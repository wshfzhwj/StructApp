package com.sf.struct.tool

import kotlinx.coroutines.*

class TestKt()

fun main(args: Array<String>) {
//    testIn()
//    testLambda()
//    testRange()
    testCoroutines()
}

fun testCoroutines() {
    GlobalScope.launch { // 在后台启动一个新的协程并继续
        delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
        println("World!") // 在延迟后打印输出
    }
    println("Hello,") // 协程已在等待时主线程还在继续
    Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活
}


fun testRange() {
    val list = listOf("a", "b", "c")

    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }
    if (list.size !in list.indices) {
        println("list size is out of valid list indices range, too")
    }
}

fun testIn() {
    val x = 10
    val y = 9
    if (x in 1..y + 1) {
        println("fits in range")
    }
}

fun testFold() {
    val items = listOf(1, 2, 3, 4, 5)

    // Lambdas 表达式是花括号括起来的代码块。
    items.fold(0) {
        // 如果一个 lambda 表达式有参数，前面是参数，后跟“->”
            acc: Int, i: Int ->
        print("acc = $acc, i = $i, ")
        val result = acc + i
        println("result = $result")
        // lambda 表达式中的最后一个表达式是返回值：
        result
    }

    // lambda 表达式的参数类型是可选的，如果能够推断出来的话：
    val joinedToString = items.fold("Elements:") { acc, i -> "$acc $i" }

    // 函数引用也可以用于高阶函数调用：
    val product = items.fold(1, Int::times)

    println("joinedToString = $joinedToString")
    println("product = $product")
}

fun testLambda() {
    //    val method : Int
//    get() = (1..1000).shuffled().first()
//
//    private inline fun<T> T.mApply(lambda:T.()->Unit):T{
//        lambda()
//        return this
//    }
}



@file:JvmName("CoroutinesStudyKt")

package com.saint.kotlin.test.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

class CoroutinesStudy {
    fun studyCoroutineScope(context: CoroutineContext) {
        val coroutineScope = CoroutineScope(context)
        coroutineScope.launch(Dispatchers.IO) {
        }

        runBlocking(Dispatchers.Main) {

        }

        coroutineScope.launch(Dispatchers.Main) {    //在 UI 线程开始
            withContext(Dispatchers.IO) {//切换到 IO 线程，并在执行完成后切回 UI 线程
            }
            // avatarIv.setImageBitmap(image)           //回到 UI 线程更新 UI
        }
    }

    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // pretend we are doing something useful here
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // pretend we are doing something useful here, too
        return 29
    }

    private fun test5() = runBlocking { // this: CoroutineScope
        launch {
            delay(200L)
            println("Task from runBlocking")
        }

        coroutineScope { // 创建一个协程作用域
            launch {
                delay(500L)
                println("Task from nested launch")
            }

            delay(100L)
            println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
        }

        println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
    }

    private fun test4() = runBlocking {
        val job = GlobalScope.launch { // 启动一个新协程并保持对这个作业的引用
            delay(1000L)
            println("World!")
        }
        println("Hello,")
        job.join() // 等待直到子协程执行结束
    }

    private fun test3() = runBlocking {
        doWorld()
    }

    private suspend fun doWorld() = coroutineScope {  // this: CoroutineScope
        launch {
            delay(1000L)
            println("World!")
        }
        println("Hello")
    }

    private fun test2() {
        //This is a delicate API and its use requires care. Make sure you fully read and understand documentation of the declaration that is marked as a delicate API.
        GlobalScope.launch { // 在后台启动一个新的协程并继续
            delay(1000L)
            println("World!")
        }
        println("Hello,") // 主线程中的代码会立即执行
        runBlocking {     // 但是这个表达式阻塞了主线程
            delay(2000L)  // ……我们延迟 2 秒来保证 JVM 的存活
        }
    }

    private fun test1() {
        GlobalScope.launch { // 在后台启动一个新的协程并继续
            //delay 是一个特殊的挂起函数 ，它不会造成线程阻塞，但是会挂起协程，并且只能在协程中使用。
            //Hello,
            //World!
            delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
            println("World!") // 在延迟后打印输出
        }
        println("Hello,") // 协程已在等待时主线程还在继续
        Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活
    }
}


fun main3() = runBlocking() {
    launch {
        test()
    }
    println("主线程")
    Thread.sleep(2000)
}

fun main2() {
    runBlocking() {
//sampleStart
        var coroutinesStudy = CoroutinesStudy()
        val time = measureTimeMillis {
            val one = async { coroutinesStudy.doSomethingUsefulOne() }
            val two = async { coroutinesStudy.doSomethingUsefulTwo() }
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
//sampleEnd
    }

}

fun main5() {
    val dispatcher = newSingleThreadContext("singleThread")
    var count = 0
    runBlocking {
        repeat(1000) {
            launch(Dispatchers.IO) {
                count++
            }
        }
        println(count)
    }
}

//fun main() {
//    main8()
//}

fun main9() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) {
            if (isActive) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    log("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            } else {
                return@launch
            }
        }
    }
    delay(1300L)
    log("main: I'm tired of waiting!")
    job.cancelAndJoin()
    log("main: Now I can quit.")
}

fun main10() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) {
            if (System.currentTimeMillis() >= nextPrintTime) {
                log("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L)
    log("main: I'm tired of waiting!")
    job.cancelAndJoin()
    log("main: Now I can quit.")
}

//fun main() = runBlocking { // this: CoroutineScope
//    launch {
//        delay(200L)
//        println("Task from runBlocking")
//    }
//
//    coroutineScope { // Creates a coroutine scope
//        launch {
//            delay(500L)
//            println("Task from nested launch")
//        }
//
//        delay(100L)
//        println("Task from coroutine scope") // This line will be printed before the nested launch
//    }
//
//    println("Coroutine scope is over") // This line is not printed until the nested launch completes
//}

//fun main(args: Array<String>) {
//    println("Start")
//
//    // 启动一个协程
//    GlobalScope.launch {
//        delay(1000)
//        println("Hello")
//    }
//
//    Thread.sleep(2000) // 等待 2 秒钟
//    println("Stop")
//}
//fun main() = runBlocking<Unit> {
//    // this: CoroutineScope
//    launch {
//        // 在 runBlocking 作用域中启动一个新协程
//        delay(1000L)
//        println("World! ${Thread.currentThread().name}")
//    }
//    println("Hello, ${Thread.currentThread().name}")
//}
fun main() = runBlocking { // this: CoroutineScope
    launch {
        delay(200L)
        log("Task from runBlocking")
    }

    coroutineScope { // Creates a coroutine scope
        launch {
            delay(500L)
            log("Task from nested launch")
        }

        delay(100L)
        log("Task from coroutine scope") // This line will be printed before the nested launch
    }

    log("Coroutine scope is over") // This line is not printed until the nested launch completes
}
//fun main() {
//    runBlocking {
//        launch {
//            // 在后台启动一个新的协程并继续
//            delay(3000L)
//            println("World!")
//        }
//    }
//    println("Hello,")
//}

//fun main8() = runBlocking<Unit> {
//    launch {
//        log("main runBlocking")
//    }
//    launch(Dispatchers.Default) {
//        log("Default")
//        launch(Dispatchers.Unconfined) {
//            log("Unconfined 1")
//        }
//    }
//    launch(Dispatchers.IO) {
//        log("IO")
//        launch(Dispatchers.Unconfined) {
//            log("Unconfined 2")
//        }
//    }
//    launch(newSingleThreadContext("MyOwnThread")) {
//        log("newSingleThreadContext")
//        launch(Dispatchers.Unconfined) {
//            log("Unconfined 4")
//        }
//    }
//    launch(Dispatchers.Unconfined) {
//        log("Unconfined 3")
//    }
//    GlobalScope.launch {
//        log("GlobalScope")
//    }
//}


fun  main6(){
    GlobalScope.launch(Dispatchers.IO) {
        delay(600)
        log("GlobalScope")
    }
    runBlocking {
        delay(500)
        log("runBlocking")
    }
    //主动休眠两百毫秒，使得和 runBlocking 加起来的延迟时间多于六百毫秒
    Thread.sleep(200)
    log("after sleep")
}

fun  main7() = runBlocking {
    launch {
        delay(100)
        log("Task from runBlocking")
    }
    coroutineScope {
        launch {
            delay(500)
            log("Task from nested launch")
        }
        delay(50)
        log("Task from coroutine scope")
    }
    log("Coroutine scope is over")
}

fun main4() {
    var count = 0
    val mutex = Mutex()

    runBlocking {
        repeat(100) {
            launch(Dispatchers.IO) {
                repeat(100) {
                    launch(Dispatchers.IO) {
                        mutex.withLock {
                            count++
                        }
                    }
                }
            }
        }
    }

    println(count)
}

suspend fun test() {
    println("协程开始")
    delay(1000)
    println("协程结束")
}

suspend fun fetchDocs() {                             // Dispatchers.Main
    val result = get("https://developer.android.com") // Dispatchers.IO for `get`
    // Dispatchers.Main
}

suspend fun get(url: String) = withContext(Dispatchers.IO) { /* ... */ }

private fun log(msg: Any?) = println("[${Thread.currentThread().name}] $msg")
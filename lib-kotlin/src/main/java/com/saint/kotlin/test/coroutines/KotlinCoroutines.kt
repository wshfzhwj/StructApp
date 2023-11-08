@file:JvmName("KotlinCoroutines")

package com.saint.kotlin.test.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
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

class KotlinCoroutines {
    suspend fun testCoroutines() {
        coroutineScope {

        }
    }

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

    private fun test3() = runBlocking {
        doWorld()
    }

    private fun test4() = runBlocking {
        val job = GlobalScope.launch { // 启动一个新协程并保持对这个作业的引用
            delay(1000L)
            println("World!")
        }
        println("Hello,")
        job.join() // 等待直到子协程执行结束
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

    private suspend fun doWorld() = coroutineScope {  // this: CoroutineScope
        launch {
            delay(1000L)
            println("World!")
        }
        println("Hello")
    }


    fun func1() = runBlocking() {
        launch {
            test()
        }
        println("主线程")
        Thread.sleep(2000)
    }

    fun func2() {
        runBlocking() {
//sampleStart
            var coroutinesStudy = KotlinCoroutines()
            val time = measureTimeMillis {
                val one = async { coroutinesStudy.doSomethingUsefulOne() }
                val two = async { coroutinesStudy.doSomethingUsefulTwo() }
                println("The answer is ${one.await() + two.await()}")
            }
            println("Completed in $time ms")
//sampleEnd
        }

    }

    fun func3() {
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
//    func8()
//}

    fun func4() = runBlocking {
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
        log("func: I'm tired of waiting!")
        job.cancelAndJoin()
        log("func: Now I can quit.")
    }

    fun func5() = runBlocking {
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
        log("func: I'm tired of waiting!")
        job.cancelAndJoin()
        log("func: Now I can quit.")
    }

    fun func6() = runBlocking { // this: CoroutineScope
        launch {
            delay(200L)
            println("Task from runBlocking")
        }

        coroutineScope { // Creates a coroutine scope
            launch {
                delay(500L)
                println("Task from nested launch")
            }

            delay(100L)
            println("Task from coroutine scope") // This line will be printed before the nested launch
        }

        println("Coroutine scope is over") // This line is not printed until the nested launch completes
    }

    fun func7() {
        println("Start")

        // 启动一个协程
        GlobalScope.launch {
            delay(1000)
            println("Hello")
        }

        Thread.sleep(2000) // 等待 2 秒钟
        println("Stop")
    }

    fun func8() = runBlocking<Unit> {
        // this: CoroutineScope
        launch {
            // 在 runBlocking 作用域中启动一个新协程
            delay(1000L)
            println("World! ${Thread.currentThread().name}")
        }
        println("Hello, ${Thread.currentThread().name}")
    }

    fun func9() = runBlocking { // this: CoroutineScope
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

    fun func10() {
        runBlocking {
            launch {
                // 在后台启动一个新的协程并继续
                delay(3000L)
                println("World!")
            }
        }
        println("Hello,")
    }

    fun func11() = runBlocking<Unit> {
        launch {
            log("func runBlocking")
        }
        launch(Dispatchers.Default) {
            log("Default")
            launch(Dispatchers.Unconfined) {
                log("Unconfined 1")
            }
        }
        launch(Dispatchers.IO) {
            log("IO")
            launch(Dispatchers.Unconfined) {
                log("Unconfined 2")
            }
        }
        launch(newSingleThreadContext("MyOwnThread")) {
            log("newSingleThreadContext")
            launch(Dispatchers.Unconfined) {
                log("Unconfined 4")
            }
        }
        launch(Dispatchers.Unconfined) {
            log("Unconfined 3")
        }
        GlobalScope.launch {
            log("GlobalScope")
        }
    }


    fun func12() {
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

    fun func13() = runBlocking {
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

    fun func14() {
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

    suspend fun fetchDocs() {                             // Dispatchers.func
        val result = get("https://developer.android.com") // Dispatchers.IO for `get`
        // Dispatchers.func
    }

    suspend fun get(url: String) = withContext(Dispatchers.IO) { /* ... */ }

    private fun log(msg: Any?) = println("[${Thread.currentThread().name}] $msg")

    fun request() = runBlocking<Unit> { // 开启一个最外层的协程
        val job: Job = launch { // 开启一个子协程
            repeat(1000) {
                println("挂起中$it")
                delay(500)
            }
        }

// launch函数有4个参数：
// 1.context: CoroutineContext = DefaultDispatcher, // 用Dispatchers指出协程运行在那个线程中
// 2.start：CoroutineStart = CoroutineStart.DEFAULT, // 协程启动的方式
// DEFAULT: 线程空闲就启动； LAZY：手动调用start才启动； ATOMIC:
// 3.parent:Job? = null
// 4. block: suspend CoroutineScope.() - Unit, 协程的内容（无返回值，无参数的挂起函数）

        // job和job2同时走

        val job2: Deferred<String> = async { // 再开启一个子协程
            delay(500)
            return@async "hello" // 协程返回字符串
        }
        println("job2的输出：${job2.await()}") // await表示等待 拿到job2的结果

        delay(1100)
        println("func: waiting")
        job.cancel()
        println("func: finish")
    }

    fun launchTest() {
        print("start")
        val job = CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            println("Cor launch")
        }
        print("end")
    }
}
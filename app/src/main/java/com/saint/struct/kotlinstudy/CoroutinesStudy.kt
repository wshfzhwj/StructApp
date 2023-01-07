@file:JvmName("CoroutinesStudyKt")

package com.saint.struct.kotlinstudy

import android.content.Context
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

class CoroutinesStudy{
    fun studyCoroutineScope(context : CoroutineContext) {
        val coroutineScope = CoroutineScope(context)
        coroutineScope.launch (Dispatchers.IO){
        }

        runBlocking(Dispatchers.Main){

        }

        coroutineScope.launch(Dispatchers.Main) {    //在 UI 线程开始
            val image = withContext(Dispatchers.IO) {//切换到 IO 线程，并在执行完成后切回 UI 线程
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

    suspend fun doWorld() = coroutineScope {  // this: CoroutineScope
        launch {
            delay(1000L)
            println("World!")
        }
        println("Hello")
    }

    private fun test2() {
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

fun main() = runBlocking<Unit> {
    //sampleStart
    var coroutinesStudy  = CoroutinesStudy()
    val time = measureTimeMillis {
        val one = async { coroutinesStudy.doSomethingUsefulOne() }
        val two = async { coroutinesStudy.doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
    //sampleEnd
}


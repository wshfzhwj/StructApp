package com.saint.kotlin.test.kotlin

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Character.getName

//fun main() {
//    runBlocking (SupervisorJob()){
//        withContext(NonCancellable) {
//            println("job: I'm running finally")
//            delay(1000L)
//            println("job: And I've just delayed for 1 sec because I'm non-cancellable")
//        }
//    }
//}

//fun main() = runBlocking {
//    val handler = CoroutineExceptionHandler { _, exception ->
//        // 第三, 这里的异常是第一个被抛出的异常对象
//        println("捕捉的异常: $exception 和被嵌套的异常: ${exception.suppressed.contentToString()}")
//    }
//    println("task main from ${Thread.currentThread()}")
//    val job = GlobalScope.launch(handler) {
//        println("task inner from ${Thread.currentThread()}")
//        launch {
//            println("task1 from ${Thread.currentThread()}")
//            try {
//                delay(Long.MAX_VALUE)
//            } finally { // 当父协程被取消时其所有子协程都被取消, finally被取消之前或者完成任务之后一定会执行
//                throw ArithmeticException() // 第二, 再次抛出异常, 异常被聚合
//            }
//        }
//
//        launch {
//            println("task2 from ${Thread.currentThread()}")
//            try {
//                delay(500)
//            } finally { // 当父协程被取消时其所有子协程都被取消, finally被取消之前或者完成任务之后一定会执行
//                throw ArithmeticException() // 第二, 再次抛出异常, 异常被聚合
//            }
//        }
//        launch {
//            println("task3 from ${Thread.currentThread()}")
//            delay(100)
//            throw IOException() // 第一, 这里抛出异常将导致父协程被取消
//        }
//        delay(Long.MAX_VALUE)
//    }
//    job.join() // 避免GlobalScope作用域没有执行完毕JVM虚拟机就退出
//}
//fun main() = runBlocking<Unit> {
//    launch {
//        System.err.println("(Main.kt:34)    后执行")
//    }
//
//    System.err.println("(Main.kt:37)    先执行")
//}
//fun main() = runBlocking<Unit> {
//    val deferred = CompletableDeferred<Int>()
//
//    launch {
//        delay(1000 )
//        deferred.complete(23)
//    }
//
//    System.err.println("(Demo.kt:72)    结果 = ${deferred.await()}")
//}

//fun main() {
//    val handler = CoroutineExceptionHandler { _, exception ->
//        // 第三, 这里的异常是第一个被抛出的异常对象
//        println("捕捉的异常: $exception")
//    }
//
//    runBlocking(handler) {
//        supervisorScope() {
////             throw Exception("a") //抛出崩溃
//
//            launch {
//                throw Exception("b")  // 不会抛出
//            }
//        }
//    }
//}

fun main() = runBlocking{
    val scope = CoroutineScope(SupervisorJob()+ Dispatchers.Main)
    //子Job2
    scope.launch(Dispatchers.Main) {
        launch(Dispatchers.Main)  {
            println("1 ${Thread.currentThread().name}")
        }
        println("2 ${Thread.currentThread().name}")
    }
    println("3 ${Thread.currentThread().name}")
}
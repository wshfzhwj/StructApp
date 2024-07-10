package com.saint.kotlin.test.kotlin

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.concurrent.Executors

class KotlinTest() {
    val lifeGoals: List<String> by lazy { initializeLifeGoals() }

    private fun initializeLifeGoals(): List<String> {
        return listOf("a")
    }

    var a = 0

    var b = B(a)

    fun sayHi(callback: (String) -> Unit) {
        callback("sss")
    }


    companion object {
        @JvmStatic
        fun testStaticMethod() {

        }
    }

    fun test(c: Int) {
        a = c
        println(b.a)
    }

    fun testList() {
        val list = mutableListOf<Int>(1, 2, 3)

        print(list.asSequence()
            .map { it * 2 }
            .filter { it % 2 == 0 }
            .count { it < 3 })

    }

    suspend fun execute(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success("a")
        } catch (e: Exception) {
            Result.Error
        }
    }

    suspend fun downloadFile(url: String): ByteArray = withContext(Dispatchers.IO) {
        return@withContext URL(url).readBytes()
    }
}

fun simpleSequence(): Sequence<Int> = sequence {
    for (i in 1..3) {
        //Thread.sleep(1000)  //直接阻塞当前线程，并非异步！
        //delay(1000) //又没有suspend修饰，肯定用不了这个挂起！就算这里用了，下面的test也用不了！
        yield(i)
    }
}

sealed interface Result {
    data class Success(val data: String) : Result
    object Error : Result
}

class B(var a: Int) {
}


//fun main() {
//    runBlocking {
//        simpleSequence().asFlow().collect{ println(it) }
//    }
//}

//fun main() {
////    KotlinTest.testStaticMethod()
////    val kotlinTest = KotlinTest()
////    kotlinTest.test(3)
//    val list = listOf("apple", "banana", "cherry", "date")
//    val result = list.groupBy { it.length }
//    println(result[4]?.get(0))
//
////    kotlinTest.test
//}

//fun main() {
//    println("start")
//    runBlocking{
//        launch(Dispatchers.Default){ task1() }
//        launch{ task2() }
//        println("called task1 and task2 from ${Thread.currentThread()}")
//    }
//    println("done")
//}



//
//fun main() {
//    Executors.newSingleThreadExecutor().asCoroutineDispatcher().use { context ->
//        println("start")
//        runBlocking{
//            launch(context, CoroutineStart.UNDISPATCHED){ task1() }
//            launch(){ task2() }
//
//            println("called task1 and task2 from ${Thread.currentThread()}")
//        }
//        println("done")
//    }
//}

fun main() {
    runBlocking {
        val ans = async(Dispatchers.Default) {
            println("执行线程是 ${Thread.currentThread()}")
            Runtime.getRuntime().availableProcessors()
        }
        println("当前线程是${Thread.currentThread()}")
        println("计算机的核数是${ans.await()}")
    }
}

fun task1(){
    println("start task1 in Thread ${Thread.currentThread()}")
    println("end task1 in Thread ${Thread.currentThread()}")
}

fun task2(){
    println("start task2 in Thread ${Thread.currentThread()}")
    println("end task2 in Thread ${Thread.currentThread()}")
}


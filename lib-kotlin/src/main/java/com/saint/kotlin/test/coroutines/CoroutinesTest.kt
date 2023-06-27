package com.saint.kotlin.test.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CoroutinesTest {
    suspend fun testCoroutines() {
        coroutineScope {

        }
    }

}

//fun main() {
//    val coroutinesTest = CoroutinesTest()
//    val scope = CoroutineScope(SupervisorJob())
//    scope.launch{
////            delay(1000)
//            println("hello")
//    }

//    runBlocking {
//        println("协程开始")
//        delay(1000)
//        println("hello world")
//    }
//
//    println("按照同步的思维，这应该在协程之后")
//    //    Thread.sleep(2000)
//    println("主程序停止")

//}

fun main() {
//    launchTest()
}



fun launchTest() {
    print("start")
    val job = CoroutineScope(Dispatchers.IO).launch {
        delay(1000)
        println("Cor launch")
    }
    print("end")
}
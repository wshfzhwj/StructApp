package com.saint.struct

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class KotlinTest {
    @Test
    fun testEquals() {
     //等待子协程执行完
        runBlocking<Unit> {
            //CoroutineScope不会继承runBlocking的属性。需要delay或者join
            val scope = CoroutineScope(Dispatchers.Default)
            scope.launch {
                delay(1000)
                Log.d("liu", "启动 job 1")
            }
            scope.launch {
                delay(1000)
                Log.d("liu", "启动 job 2")
            }
            //需要挂起，等待scope执行完
            delay(2000)
        }
    }


}
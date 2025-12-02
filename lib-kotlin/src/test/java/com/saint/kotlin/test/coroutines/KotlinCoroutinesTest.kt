package com.saint.kotlin.test.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class KotlinCoroutinesTest {
    @Test
    fun testCoroutineExample() = runBlocking {
        println("Start test")
       val job =  launch {
            repeat(10){
                print('a')
            }
        }
        val job2 = launch {
            repeat(10){
                print('b')
            }
        }
        job.join()
        job2.join()
        println("End test")
        // Add delay to allow coroutines to complete
    }
}

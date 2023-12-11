package com.saint.kotlin.test.kotlin

import android.util.Log

class KotlinTest {
    fun sayHi(callback: (String) -> Unit) {
        callback("sss")
    }

    companion object {
        @JvmStatic
        fun testStaticMethod(){

        }
    }

    val test = {
        println("无参数")
    }
}
fun main() {
//    KotlinTest.testStaticMethod()
    val kotlinTest = KotlinTest()
    kotlinTest.sayHi { println("Hello, $it!") }
//    kotlinTest.test
}
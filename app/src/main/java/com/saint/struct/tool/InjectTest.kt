package com.saint.struct.tool

import com.saint.annotation.PluginTest

class InjectTest {
    @PluginTest
    fun sayHello() {
        println("你好")
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
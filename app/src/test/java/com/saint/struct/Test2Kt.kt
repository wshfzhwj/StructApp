package com.saint.struct

interface Base {
    fun print()
}

// 实现此接口的被委托的类
class BaseImpl(val x: Int) : Base {
    override fun print() {
        print(x)
    }
}

// 通过关键字 by 建立委托类
class Derived(b: Base) : Base by b

fun main() {
//    testBy()
//    testWith()

}

fun testWith() {
    val myTurtle = Turtle()
    with(myTurtle) { // 画一个 100 像素的正方形
        penDown()
        for (i in 1..4) {
            forward(100.0)
            turn(90.0)
        }
        penUp()
    }
}

fun testBy() {
    val b = BaseImpl(10)
    Derived(b).print() // 输出 10
}

fun studyFor() {
    val items = listOf("apple", "banana", "kiwifruit")
    for (index in items.indices) {
        println("item at $index is ${items[index]}")
    }

    val items2 = listOf("apple", "banana", "kiwifruit")
    for (item in items2) {
        println(item)
    }
}


fun describe(obj: Any): String =
    when (obj) {
        1 -> "One"
        "Hello" -> "Greeting"
        is Long -> "Long"
        !is String -> "Not a string"
        else -> "Unknown"
    }

class Turtle {
    fun penDown() {}
    fun penUp() {}
    fun turn(degrees: Double) {
        println("degrees $degrees")
    }

    fun forward(pixels: Double) {
        println("pixels $pixels")
    }
}


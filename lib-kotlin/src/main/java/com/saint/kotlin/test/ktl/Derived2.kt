package com.saint.kotlin.test.ktl

import java.util.Locale

class Derived2(
    name: String, val lastName: String,
) : Base2(name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    .also { println("Argument for Base: $it") }) {
    init {
        println("Initializing Derived")
    }

    override val size: Int =
        (super.size + lastName.length).also { println("Initializing size in Derived: $it") }
}

fun main() {
//    println("Constructing Derived(\"hello\", \"world\")")
//    val d = Derived2("hello", "world")

    println(lazyValue)   // 第一次执行，执行两次输出表达式
    println(lazyValue)   // 第二次执行，只输出返回值
}


val lazyValue: String by lazy {
    println("computed!")     // 第一次调用输出，第二次调用不执行
    "Hello"
}

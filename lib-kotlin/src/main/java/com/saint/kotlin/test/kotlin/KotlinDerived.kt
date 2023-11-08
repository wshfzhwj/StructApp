package com.saint.kotlin.test.kotlin

import java.util.Locale

class KotlinDerived(
    name: String, private val lastName: String,
) : KotlinBaseClass(name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
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
    val repeatFun: String.(Int) -> String = { times -> this.repeat(times) }

    //    val twoParameters: (String, Int) -> String = repeatFun // OK
//
    fun runTransformation(f: (String, Int) -> String): String {
        return f("hello", 3)
    }

    val result = runTransformation(repeatFun) // OK
//    //sampleEnd
    println("result = $result")


//    println(lazyValue)   // 第一次执行，执行两次输出表达式
//    println()
//    println(lazyValue)   // 第二次执行，只输出返回值
}


//private val lazyValue: String by lazy {
//    println("computed!")     // 第一次调用输出，第二次调用不执行
//    "Hello"
//}
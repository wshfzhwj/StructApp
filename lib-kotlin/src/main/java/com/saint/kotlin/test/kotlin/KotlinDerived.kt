package com.saint.kotlin.test.kotlin

import java.util.Locale

/**
 * 类委托和委托属性：
 * 委托是一种设计模式，它的基本理念就是：操作对象自己不会去处理某段逻辑，
 * 而是会把工作委托给另外一个辅助对象去处理。kotlin也支持委托功能，
 * 并且将委托功能分为了两种：类委托和委托属性。
 * 首先类委托，它的核心思想就是将一个类的具体实现委托给另一个类去实现
 *
 * 委托模式的意义就是，让大部分方法实现调用辅助对象的方法，少部分方法实现由自己重写，或者加入一些新的东西
 * 委托属性的核心思想是将一个属性的具体实现委托给另一个类去完成
 */
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
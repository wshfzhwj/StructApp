package com.saint.kotlin.test.kotlin

//既然函数是一种类型，那么是可以获取实例化的实例，主要可以通过一下三种方式
//
//:: 双冒号操作符表示对函数的引用
//lambda表达式
//匿名函数
fun main(args: Array<String>) {
    // 引用函数
    println(test(1, 2, ::add))
    // 匿名函数
    val add = fun(a: Int, b: Int): Int {
        return a + b
    }
    println(test(3, 4, add))
    // lambda表达式
    //println(test(5, 6, { a, b -> a + b }))// lambda作为最后一个参数可以提到括号外
    println(test(5, 6) { a, b -> a + b })
}

fun test(a: Int, b: Int, f: (Int, Int) -> Int): Int {
    return f(a, b)
}

fun add(a: Int, b: Int): Int {
    return a + b
}
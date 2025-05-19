package com.saint.kotlin.test.kotlin

class KotlinLabel {
    private val ints = arrayListOf(1, 0, 2, 3)
    fun foo() {
        ints.forEach lit@{
            if (it == 0) return@lit
            print(it)
        }
        println()
        ints.forEach { print(it) }
        println()
        ints.forEach {
            if (it == 0) return@forEach
            print(it)
        }
        println()
        run loop@{
            listOf(1, 2, 3, 4, 5).forEach {
                if (it == 3) return@loop // 从传入 run 的 lambda 表达式非局部返回
                print(it)
            }
        }

    }

    fun foo2() {
        ints.forEach(fun(value: Int) {
            if (value == 0) return
            print(value)
        })
    }
}

fun main() {
    val label = KotlinLabel()
    label.foo()
}


package com.saint.kotlin.test.kotlin

class KotlinLabel(name: String) {
    constructor(firstName: String, lastName: String) : this(firstName.plus(lastName)) {
        //次构造函数
    }

    private val ints = arrayListOf(1, 0, 2, 3)
    fun foo() {
        ints.forEach lit@{
            if (it == 0) return@lit
            print(it)
        }

        ints.forEach { print(it) }

        ints.forEach {
            if (it == 0) return@forEach
            print(it)
        }

    }

    fun foo2() {
        ints.forEach(fun(value: Int) {
            if (value == 0) return
            print(value)
        })
    }
}


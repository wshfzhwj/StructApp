package com.saint.kotlin.test.ktl

open class Base2(val name: String) {

    init { println("Initializing Base study") }

    open val size: Int =
        name.length.also { println("Initializing size in Base: $it") }
}
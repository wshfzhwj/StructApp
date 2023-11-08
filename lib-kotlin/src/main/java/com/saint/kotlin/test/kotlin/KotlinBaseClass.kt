package com.saint.kotlin.test.kotlin

open class KotlinBaseClass(val name: String) {

    init { println("Initializing Base study") }

    open val size: Int =
        name.length.also { println("Initializing size in Base: $it") }
}
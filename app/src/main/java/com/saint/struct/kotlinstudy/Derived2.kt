package com.saint.struct.kotlinstudy

class Derived2(name: String,val lastName: String,
) : Base2(name.capitalize().also { println("Argument for Base: $it") }) {

    init { println("Initializing Derived") }

    override val size: Int =
        (super.size + lastName.length).also { println("Initializing size in Derived: $it") }
}

fun main() {
    println("Constructing Derived(\"hello\", \"world\")")
    val d = Derived2("hello", "world")
}
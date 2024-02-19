package com.saint.kotlin.test.kotlin

open class ConstructorExample(val name: String) {
//    init{
//        println("name is $name ")
//    }
}

class ChildConstructor(name: String, val age: Int = 20,grade :String = "pass") : ConstructorExample(name) {

    constructor(name: String, age: Int) : this(name, age,"fail")

    init{
        println("name is $name age is $age grade is $grade")
    }
}


fun main() {
    val parent = ConstructorExample("saint")
    val child = ChildConstructor("a", 10);
    val child3 = ChildConstructor("b");
    val child2 = ChildConstructor("c", 21, "well")
}
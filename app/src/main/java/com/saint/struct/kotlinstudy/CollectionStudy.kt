package com.saint.struct.kotlinstudy

class CollectionStudy{
    //延迟属性
    val p:String by lazy{
        println("set lazyValue")
        "lazyInit"
    }

}

object Resource {
    val name = "Name"
}

fun String.customLowercase() {"i from custom" + this.lowercase()}

fun main(args: Array<String>){
    //只读list
    val list = listOf("a", "b", "c")

    //只读 map
    val map = mapOf("a" to 1, "b" to 2, "c" to 3)

    //可变map
    var mutableMap = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
    mutableMap["key"] = 3

    //遍历 map 或者 pair 型 list
    for ((k, v) in map) {
        println("$k -> $v")
    }

    //区间迭代
    for (i in 1..10) {}  // 闭区间：包含 100
    for (i in 1 until 10) {} // 半开区间：不包含 100
    for (x in 2..10 step 2) {}
    for (x in 10 downTo 1) {}
    (1..10).forEach {}

    println("Convert this to camelcase".lowercase())
    println("Resource.name = $Resource.name")


}
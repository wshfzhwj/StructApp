package com.saint.kotlin.test.kotlin

class KotlinCollection {

    fun testReduce() {
//    val ranges = listOf(setOf(1..5), setOf(3..8), setOf(6..10))
        val ranges = listOf(setOf(1, 2, 3, 4, 5), setOf(3, 4, 5, 6, 7, 8), setOf(8, 9, 10))
        val combinedRange = ranges.reduce { acc, range -> acc.union(range) }
        println(combinedRange)
    }

    fun testFlatMap() {
        val list = listOf(
            1..20, 2..5
        )
        println(list)
        val newList = list.flatten().map { it > 2 } // 降维 再映射
//        val newList2 = list.flatMap// 降维 再映射
        println(newList)
    }

    fun diffMapWithFlatMap() {
        val list = listOf("张三", "李四", "王五")
//    val list4: List<String> = listOf("张三", "李四", "王五")
//    val newList: List<String> = list4.map {
//        "内容是:$it"  //每次返回一个 String
//    }.map {
//        "$it, 长度是：${it.length}"
//    }.flatMap {
//        listOf("$it,在学习C++", "$it,在学习Java", "$it,在学习Kotlin")
//    }
//    println("newList长度 ${newList.size}")
//    newList.forEach { println(it) }


        val newList2: List<String> = list.flatMap {
            listOf("$it,在学习C++", "$it,在学习Java", "$it,在学习Kotlin")
        }
        println("newList2长度 ${newList2.size}")
        println(newList2)
    }

}



fun main() {
    //只读list
    val list = listOf("a", "b", "c")

    //只读 map
    val map = mapOf("a" to 1, "b" to 2, "c" to 3)

    //可变map
    val mutableMap = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
    mutableMap["key"] = 3

    //遍历 map 或者 pair 型 list
    for ((k, v) in map) {
        println("$k -> $v")
    }

    //区间迭代
    for (i in 1..10) {
        print(i)
    }  // 闭区间：包含 100
    println()
    for (i in 1 until 10) {
        print(i)
    } // 半开区间：不包含 100
    println()
    for (x in 2..10 step 2) {
        print(x)
    }
    println()
    for (x in 10 downTo 1) {
        print(x)
    }
    println()
    (1..10).forEach { print(it) }
    println("------------------------------")


    val list3 = listOf("张三", "李四", "王五")
//    val list4: List<String> = listOf("张三", "李四", "王五")
//    val newList: List<String> = list4.map {
//        "内容是:$it"  //每次返回一个 String
//    }.map {
//        "$it, 长度是：${it.length}"
//    }.flatMap {
//        listOf("$it,在学习C++", "$it,在学习Java", "$it,在学习Kotlin")
//    }
//    println("newList长度 ${newList.size}")
//    newList.forEach { println(it) }


    val newList2: List<String> = list3.flatMap {
        listOf("$it,在学习C++", "$it,在学习Java", "$it,在学习Kotlin")
    }
    println("newList2长度 ${newList2.size}")
    println(newList2)
}
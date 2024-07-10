package com.saint.struct

import org.junit.Test

class KotlinTest {
    @Test
    fun testEquals() {
        val user = com.saint.kotlin.test.kotlin.User("a", "a")
        val user2 = com.saint.kotlin.test.kotlin.User("a", "a")
        println((user === user2))
        println((user == user2))
    }


}
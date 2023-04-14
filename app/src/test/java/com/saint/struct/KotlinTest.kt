package com.saint.struct

import com.saint.struct.bean.User
import org.junit.Test

class KotlinTest {
    @Test
    fun testEquals() {
        val user = User("a", "a")
        val user2 = User("a", "a")
        println((user === user2))
        println((user == user2))
    }
}
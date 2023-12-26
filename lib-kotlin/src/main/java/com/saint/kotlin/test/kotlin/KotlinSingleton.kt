package com.saint.kotlin.test.kotlin

class KotlinSingleton private constructor() {
    private var users: MutableList<User>

    companion object {
        @Volatile
        private var INSTANCE: KotlinSingleton? = null

        fun getInstance(): KotlinSingleton {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: KotlinSingleton().also {
                    // 初始化工作
                    INSTANCE = it
                }
            }
        }
    }

    // keeping the constructor private to enforce the usage of getInstance
    init {
        val user1 = User("Jane", "")
        val user2 = User("John", null)
        val user3 = User("Anne", "Doe")
        users = mutableListOf(user1, user2, user3)
    }

    fun getUsers(): List<User> {
        return users
    }

    val formattedUserNames: List<String>
        get() {
            val userNames: MutableList<String> = ArrayList(users.size)
            for ((firstName, lastName) in users) {
                val name: String = if (lastName != null) {
                    if (firstName != null) {
                        "$firstName $lastName"
                    } else {
                        lastName
                    }
                } else firstName ?: "Unknown"
                userNames.add(name)
            }
            return userNames
        }
}
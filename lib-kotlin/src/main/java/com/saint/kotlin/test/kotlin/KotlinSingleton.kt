package com.saint.kotlin.test.kotlin
class KotlinSingleton
private constructor(){
    private var users: MutableList<User>? = null

    companion object {
        @Volatile
        private var INSTANCE: KotlinSingleton? = null
        val instance: KotlinSingleton?
            get() {
                if (INSTANCE == null) {
                    synchronized(KotlinSingleton::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = KotlinSingleton()
                        }
                    }
                }
                return INSTANCE
            }
    }

    // keeping the constructor private to enforce the usage of getInstance
    init {
        val user1 = User("Jane", "")
        val user2 = User("John", null)
        val user3 = User("Anne", "Doe")
        users = mutableListOf(user1,user2,user3)
//        users!!.add(user1)
//        users!!.add(user2)
//        users!!.add(user3)
    }

    fun getUsers(): List<User>? {
        return users
    }

    val formattedUserNames: List<String>
        get() {
            val userNames: MutableList<String> = ArrayList(users!!.size)
            for ((firstName, lastName) in users!!) {
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
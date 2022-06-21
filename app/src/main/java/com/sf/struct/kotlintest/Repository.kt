package com.sf.struct.kotlintest

import com.sf.struct.bean.User

class Repository
private constructor() {
    private var users: MutableList<User>? = null

    companion object {
        private var INSTANCE: Repository? = null
        val instance: Repository?
            get() {
                if (INSTANCE == null) {
                    synchronized(Repository::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = Repository()
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
        users = ArrayList()
        users!!.add(user1)
        users!!.add(user2)
        users!!.add(user3)
    }

    fun getUsers(): List<User>? {
        return users
    }

    val formattedUserNames: List<String>
        get() {
            val userNames: MutableList<String> = ArrayList(users!!.size)
            for ((firstName, lastName) in users!!) {
                var name: String?
                if (lastName != null) {
                    if (firstName != null) {
                        name = "$firstName $lastName"
                    } else {
                        name = lastName
                    }
                } else if (firstName != null) {
                    name = firstName
                } else {
                    name = "Unknown"
                }
                userNames.add(name)
            }
            return userNames
        }



}
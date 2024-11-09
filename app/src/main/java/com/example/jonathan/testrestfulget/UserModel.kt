package com.example.jonathan.testrestfulget

/**
 * Model of MVVM
 */

// Data classes

data class User(val name: String, val icon: String)

// Data repository

class UserRepository {
    fun getUsers(): List<User> {
        return listOf(
            User("User1", "user1.jpg"),
            User("User2", "user2.jpg")
        )
    }
}

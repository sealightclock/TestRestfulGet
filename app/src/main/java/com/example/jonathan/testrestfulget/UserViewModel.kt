package com.example.jonathan.testrestfulget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel of MVVM
 */

class UserViewModel : ViewModel() {
    // Refer to the repository:
    private val repository = UserRepository()

    // Data:
    lateinit var users: List<User>

    init {
        loadDataFromTest()
    }

    // This appears to be needed to have "users" initialized
    private fun loadDataFromTest() {
        users = repository.getUsersFromTest()
    }

    fun loadDataFromRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            users = repository.getUsersFromNetwork()
        }
    }
}

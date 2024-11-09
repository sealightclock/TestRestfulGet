package com.example.jonathan.testrestfulget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
        loadDataFromRepository()
    }

    private fun loadDataFromRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            users = repository.getUsers()
        }

        viewModelScope.launch(Dispatchers.IO) {
            users = repository.getUsersFromNetwork()
        }
    }
}

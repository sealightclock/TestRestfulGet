package com.example.jonathan.testrestfulget

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This file contains the ViewModel component of MVVM
 */

private const val TAG = "TRST: UserViewModel"

class UserViewModel : ViewModel() {
    // Refer to the repository:
    private val repository = UserRepository()

    // Data:
    lateinit var users: List<User>

    init {
        loadDataFromTest()
    }

    // This provides a list of test users before the actual users are obtained from the internet.
    private fun loadDataFromTest() {
        Log.d(TAG, "loadDataFromTest")

        users = repository.getUsersFromTest()
    }

    // This provides a list of users from the internet, with a delay:
    fun loadDataFromNetwork() {
        Log.d(TAG, "loadDataFromNetwork")

        viewModelScope.launch(Dispatchers.IO) {
            users = repository.getUsersFromNetwork()
        }
    }
}

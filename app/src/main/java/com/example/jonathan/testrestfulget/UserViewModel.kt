package com.example.jonathan.testrestfulget

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * This file contains the ViewModel component of MVVM
 */

private const val TAG = "TRST: UserViewModel"

class UserViewModel : ViewModel() {
    // Refer to the repository:
    private val repository = UserRepository()

    // Data:
    private var _users = MutableLiveData<List<User>>(emptyList())
    var users: LiveData<List<User>> = _users

    init {
        loadDataFromTest()
    }

    // This provides a list of test users before the actual users are obtained from the internet.
    private fun loadDataFromTest() {
        Log.d(TAG, "loadDataFromTest")

        _users.value = repository.getUsersFromTest()
    }

    // This provides a list of users from the internet, with a delay:
    fun loadDataFromNetwork() {
        Log.d(TAG, "loadDataFromNetwork")

        _users.postValue(repository.getUsersFromNetwork())
    }
}

package com.example.jonathan.testrestfulget

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * This file contains the ViewModel component of MVVM
 */

private const val TAG = "TRST: UserViewModel"

class UserViewModel : ViewModel() {
    // Refer to the repository:
    private val repository = UserRepository()

    /* // Data (using the MutableLiveData and LiveData pair, without initial value):
    private var _users = MutableLiveData<List<User>>()
    var users: LiveData<List<User>> = _users */

    // Data (using the MutableStateFlow and StateFlow pair, with initial value):
    private var _users = MutableStateFlow<List<User>>(emptyList())
    var users: StateFlow<List<User>> = _users

    /**
     * !!! Key keyword "suspend" even thought the editor suggests removing it.
     * Keyword 'suspend" is not required here. However, there are many advantages of using it here:
     * [1] This indicates that this function takes a long time to complete, so should be called
     *     in a coroutine with "launch".
     * [2] This can not be directly called by a non-suspend function.
     * [3] This suspend function call call other suspend functions.
     * [4] Because we know this function is to be called in a coroutine, we cannot use:
     *         _users.value = newValue
     *     Instead, we have to use:
     *         _users.postValue(newValue)
     */
    // This provides a list of users from the internet, with a delay:
    @Suppress("RedundantSuspendModifier")
    suspend fun getData() {
        Log.d(TAG, "getData")

        //_users.postValue(repository.fetchDataFromWebByHttpUrlConnection())

        _users.value = repository.fetchDataFromWebByHttpUrlConnection()
    }
}

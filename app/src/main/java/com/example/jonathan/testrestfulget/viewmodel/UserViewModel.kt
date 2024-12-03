package com.example.jonathan.testrestfulget.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jonathan.testrestfulget.model.User
import com.example.jonathan.testrestfulget.model.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * This file contains the ViewModel component of MVVM
 */

private const val TAG = "TRST: UserViewModel"

enum class DataSourceType {
    Test,
    WebByHttpUrl,
    WebByOkhttp,
    WebByRetrofit
}

class UserViewModel : ViewModel() {
    // Refer to the repository:
    private val repository = UserRepository()

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
    // This get data from a specific data source:
    //@Suppress("RedundantSuspendModifier")
    fun getData(dataSourceType: DataSourceType) {
        Log.d(TAG, "getData: dataSourceType=[$dataSourceType]")

        viewModelScope.launch(Dispatchers.IO) {
            val newUsers =
                when (dataSourceType) {
                    DataSourceType.Test -> repository.fetchDataByTest()
                    DataSourceType.WebByHttpUrl -> repository.fetchDataFromWebByHttpUrl()
                    DataSourceType.WebByOkhttp -> repository.fetchDataFromWebByOkhttp()
                    DataSourceType.WebByRetrofit -> repository.fetchDataFromWebByRetrofit()
                }

            // Logging:
            newUsers.forEach { user ->
                Log.v(TAG, "getData: dataSourceType=[$dataSourceType]: user.name=[${user.name}]")
            }

            // TODO: Try to understand why MutableStateFlow _users can be set a value directly:
            _users.value = newUsers
        }
    }
}

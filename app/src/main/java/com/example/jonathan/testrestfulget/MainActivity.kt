package com.example.jonathan.testrestfulget

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

/**
 * This is a driver Activity to test RESTful GET implementation.
 */

private const val TAG = "TRST: MainActivity"

class MainActivity : ComponentActivity() {
    // Create ViewModel:
    private lateinit var viewModel: UserViewModel
    private var dataSourceType by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")

        super.onCreate(savedInstanceState)

        // Initialize ViewModel:
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        // Update ViewModel using the selected data source:
        viewModel.getData(DataSourceType.Test)
        dataSourceType = DataSourceType.Test.ordinal
        // Observe ViewModel (for debugging purposes only):
        viewModel.viewModelScope.launch {
            viewModel.users.collect { users ->
                // Handle the new state value here
                Log.d(TAG, "onCreate: viewModel.users.collect: $users")
            }
        }

        setContent {
            // Connect to View which connects to ViewModel of MVVM:
            UserListView(viewModel)
        }
    }

    override fun onResume() {
        Log.d(TAG, "onResume")

        super.onResume()

        // Refresh the UI, and try another way of getting data:
        val numDataSourceTypes = DataSourceType.entries.size
        dataSourceType = (dataSourceType + 1) % numDataSourceTypes
        viewModel.getData(enumValues<DataSourceType>()[dataSourceType])
    }
}

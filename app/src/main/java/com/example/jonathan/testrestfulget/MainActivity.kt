package com.example.jonathan.testrestfulget

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This is a driver Activity to test RESTful GET implementation.
 */

private const val TAG = "TRST: MainActivity"

class MainActivity : ComponentActivity() {
    // Create ViewModel:
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")

        super.onCreate(savedInstanceState)

        // Initialize ViewModel:
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // Update ViewModel:
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            viewModel.getData()
        }

        // Observe ViewModel changes (for debugging purposes only):
        /*
        viewModel.users.observe(this) {
            Log.d(TAG, "onCreate: viewModel.users.observe: $it")
        }
         */

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
    }
}

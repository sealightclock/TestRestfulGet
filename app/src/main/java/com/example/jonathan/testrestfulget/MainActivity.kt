package com.example.jonathan.testrestfulget

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This is a driver Activity to test RESTful GET implementation.
 */

private const val TAG = "TRST: MainActivity"

class MainActivity : ComponentActivity() {
    // Create ViewModel:
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")

        super.onCreate(savedInstanceState)

        // Refresh ViewModel:
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            viewModel.loadDataFromNetwork()
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

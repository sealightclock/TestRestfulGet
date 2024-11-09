package com.example.jonathan.testrestfulget

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "TRST: MainActivity"

class MainActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")

        super.onCreate(savedInstanceState)

        //enableEdgeToEdge()

        // Refresh ViewModeL:
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            viewModel.loadDataFromRepository()
        }
        
        setContent {
            /*TestRestfulGetTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }*/

            // Connect to View which connects to ViewModel of MVVM:
            UserListView(viewModel)
        }
    }

    override fun onResume() {
        Log.d(TAG, "onResume")

        super.onResume()
    }
}

/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestRestfulGetTheme {
        Greeting("Android")
    }
}
*/

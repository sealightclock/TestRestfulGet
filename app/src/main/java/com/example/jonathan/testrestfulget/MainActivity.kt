package com.example.jonathan.testrestfulget

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import com.example.jonathan.testrestfulget.ui.theme.TestRestfulGetTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "TRST: MainActivity"

class MainActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")

        super.onCreate(savedInstanceState)

        //enableEdgeToEdge()

        // Create ViewModel
        //val viewModel: UserViewModel by viewModels()

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

        // Test
        val userFromNetwork = UserFromNetwork()
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            userFromNetwork.getUserData()
        }
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

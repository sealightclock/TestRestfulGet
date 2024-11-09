package com.example.jonathan.testrestfulget

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

private const val TAG = "TRST: UserModel"

/**
 * Model of MVVM
 */

// Data classes

data class User(val name: String, val photo: String)

// Data repository

class UserRepository {
    fun getUsers(): List<User> {
        Log.d(TAG, "UserRepository: getUsers")

        return listOf(
            User("User1", "user1.jpg"),
            User("User2", "user2.jpg")
        )
    }
}

// Data from network

class UserFromNetwork {
    fun getUserData() {
        Log.d(TAG, "UserFromNetwork: getUserData")

        //val url = URL("https://www.example.com/")
        val url = URL("https://fake-json-api.mock.beeceptor.com/users")

        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            Log.v(TAG, "UserFromNetwork: getUserData: HTTP_OK")

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?
            val response = StringBuilder()

            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }

            reader.close()

            Log.v(TAG, "UserFromNetwork: getUserData: response=\n$response")
        } else {
            Log.v(TAG, "UserFromNetwork: getUserData: responseCode=\n${connection.responseCode}")
        }

        connection.disconnect()
    }
}

package com.example.jonathan.testrestfulget

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * This file contains the Model component of MVVM.
 */

private const val TAG = "TRST: UserModel"

// Test URL - a Json file with a list of users:
val url = URL("https://fake-json-api.mock.beeceptor.com/users")

// Data classes
data class User(val name: String, val photo: String)

// Data repository to handle data from various sources
class UserRepository {
    // This gets users from an internet Json file:
    fun getUsersFromNetwork(): List<User> {
        Log.d(TAG, "UserRepository: getUsersFromNetwork")

        // Get Json string from network:
        val userFromNetwork = UserFromNetwork()
        val jsonString = userFromNetwork.getUserData()

        // Convert Json string to data class objects using Gson:
        val gsonUtil = GsonUtil()
        val users = gsonUtil.fromJsonToDataClass(jsonString)

        for (user in users) {
            Log.v(TAG, "getUsersFromNetwork: user=[${user.name}, ${user.photo}")
        }

        return users
    }
}

// Data from network
class UserFromNetwork {
    // This is based on internet search results:
    fun getUserData(): String {
        Log.d(TAG, "UserFromNetwork: getUserData")

        val response = StringBuilder()

        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            Log.v(TAG, "UserFromNetwork: getUserData: HTTP_OK")

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }

            reader.close()

            Log.v(TAG, "UserFromNetwork: getUserData: response=\n$response")
        } else {
            Log.v(TAG, "UserFromNetwork: getUserData: responseCode=\n${connection.responseCode}")
        }

        connection.disconnect()

        return response.toString()
    }
}

// Gson utility class
class GsonUtil {
    // This converts Json string to a list of Data Class object of type User:
    fun fromJsonToDataClass(jsonString: String): List<User> {
        Log.d(TAG, "GsonUtil: fromJsonToDataClass")

        val gson = Gson()

        // Define the type for the list of User objects:
        val userType = object : TypeToken<List<User>>() {}.type

        // Deserialize the JSON string into a list of User objects:
        val users = gson.fromJson<List<User>>(jsonString, userType)

        return users
    }
}

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
    fun fetchDataFromWebByHttpUrlConnection(): List<User> {
        Log.d(TAG, "UserRepository: fetchDataFromWebByHttpUrlConnection")

        // Get Json string from network:
        val userFromNetwork = DataFromWebByHttpUrlConnection()
        val jsonString = userFromNetwork.fetchData()

        // Convert Json string to data class objects using Gson:
        val gsonUtil = GsonUtil()
        val users = gsonUtil.fromJsonToDataClass(jsonString)

        for (user in users) {
            Log.v(TAG, "UserRepository: fetchDataFromWebByHttpUrlConnection: user=[${user.name}, ${user.photo}")
        }

        return users
    }
}

// Data from web by HttpURLConnection
class DataFromWebByHttpUrlConnection {
    // This is based on internet search results:
    fun fetchData(): String {
        Log.d(TAG, "DataFromWebByHttpUrlConnection: fetchData")

        val response = StringBuilder()

        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            Log.v(TAG, "DataFromWebByHttpUrlConnection: fetchData: HTTP_OK")

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }

            reader.close()

            Log.v(TAG, "DataFromWebByHttpUrlConnection: fetchData: response=\n$response")
        } else {
            Log.v(TAG, "DataFromWebByHttpUrlConnection: fetchData: responseCode=\n${connection.responseCode}")
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

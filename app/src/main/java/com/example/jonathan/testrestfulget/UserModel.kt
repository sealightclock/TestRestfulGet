package com.example.jonathan.testrestfulget

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
    fun getUserData(): String {
        Log.d(TAG, "UserFromNetwork: getUserData")

        val response = StringBuilder()

        //val url = URL("https://www.example.com/")
        val url = URL("https://fake-json-api.mock.beeceptor.com/users")

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

class GsonUtil {
    fun fromJsonToDataClass() {
        Log.d(TAG, "GsonUtil: fromJsonToDataClass")

        val jsonString = """
        {
            "name": "User1",
            "photo": "user1.jpg"
        }
    """

        val gson = Gson()
        val user = gson.fromJson(jsonString, User::class.java)

        Log.d(TAG, "GsonUtil: fromJsonToDataClass: user.name=${user.name}, user.photo=${user.photo}")
    }

    fun fromJsonToDataClass2(jsonString: String): List<User> {
        Log.d(TAG, "GsonUtil: fromJsonToDataClass2")

        val gson = Gson()

        // Define the type for the list of User objects
        val userType = object : TypeToken<List<User>>() {}.type

        // Deserialize the JSON string into a list of User objects
        val users = gson.fromJson<List<User>>(jsonString, userType)

        return users
    }
}

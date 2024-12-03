package com.example.jonathan.testrestfulget.model

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val TAG = "TRST: GsonUtil"

// Gson utility class
class GsonUtil {
    // This converts Json string to a list of Data Class object of type User:
    fun fromJsonToDataClass(jsonString: String): List<User> {
        Log.d(TAG, "fromJsonToDataClass")

        val gson = Gson()

        // Define the type for the list of User objects:
        val userType = object : TypeToken<List<User>>() {}.type

        // Deserialize the JSON string into a list of User objects:
        val users = gson.fromJson<List<User>>(jsonString, userType)

        return users
    }
}

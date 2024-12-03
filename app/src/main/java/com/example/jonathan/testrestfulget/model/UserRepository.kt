package com.example.jonathan.testrestfulget.model

import android.util.Log
import com.example.jonathan.testrestfulget.model.httpurl.HttpUrlClient
import com.example.jonathan.testrestfulget.model.okhttp.fetchUrl
import com.example.jonathan.testrestfulget.model.retrofit.RetrofitInstance
import retrofit2.awaitResponse

private const val TAG = "TRST: UserRepository"

/**
 * This is the Model part of the MVVM Clean architecture.
 * - data classes
 * - repository to handle different data sources
 */

// The complete Url is: BASE_URL + RELATIVE_URL:
const val BASE_URL = "https://fake-json-api.mock.beeceptor.com/"
const val RELATIVE_URL = "users"

// Data repository to fetch data from various sources
class UserRepository {
    // This fetches test data without calling RESTful API, useful sometimes:
    @Suppress("RedundantSuspendModifier")
    suspend fun fetchDataByTest(): List<User> {
        Log.d(TAG, "fetchDataByTest")

        return listOf(
            User("User1", "user1.jpg"),
            User("User2", "user2.jpg")
        )
    }

    // This gets users from an internet Json file:
    fun fetchDataFromWebByHttpUrl(): List<User> {
        Log.d(TAG, "fetchDataFromWebByHttpUrl")

        // Get Json string from network:
        val httpUrlClient = HttpUrlClient()

        val jsonString = httpUrlClient.fetchData()

        Log.v(TAG, "fetchDataFromWebByHttpUrl: jsonString=[$jsonString]")

        // Convert Json string to data class objects using Gson:
        val gsonUtil = GsonUtil()
        val users = gsonUtil.fromJsonToDataClass(jsonString)

        for (user in users) {
            Log.v(TAG, "fetchDataFromWebByHttpUrl: user=[${user.name}, ${user.photo}")
        }

        return users
    }

    // This fetches users from the web by Okhttp:
    fun fetchDataFromWebByOkhttp(): List<User> {
        Log.d(TAG, "fetchDataFromWebByOkhttp")

        // Get Json string from network:
        val jsonString = fetchUrl(BASE_URL + RELATIVE_URL)

        Log.v(TAG, "fetchDataFromWebByOkhttp: jsonString=[$jsonString]")

        // Convert Json string to data class objects using Gson:
        val gsonUtil = GsonUtil()
        val users = gsonUtil.fromJsonToDataClass(jsonString)

        for (user in users) {
            Log.v(TAG, "fetchDataFromWebByOkhttp: user=[${user.name}, ${user.photo}")
        }

        return users
    }

    // This fetches data from the web by Retrofit RESTful API:
    suspend fun fetchDataFromWebByRetrofit(): List<User> {
        Log.d(TAG, "fetchDataFromWebByRetrofit")

        val response = RetrofitInstance.retrofitApi.getUsers().awaitResponse()

        val newPosts = if (response.isSuccessful) {
            Log.v(TAG, "fetchDataFromWebByRetrofit: response.isSuccessful")

            response.body() ?: emptyList()
        } else { // Handle error cases
            Log.e(TAG, "fetchDataFromWebByRetrofit: response.message=[${response.message()}]")

            emptyList()
        }

        return newPosts
    }
}

package com.example.jonathan.testrestfulget.model.okhttp

import okhttp3.OkHttpClient
import okhttp3.Request

// Initialize OkHttpClient
val client = OkHttpClient()

fun fetchUrl(url: String): String {
    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).execute().use { response ->
        return if (response.isSuccessful) {
            //response.body?.string() ?: "Empty response"
            response.toString()
        } else {
            //"Request failed: ${response.message}"
            "Request failed"
        }
    }
}

package com.example.jonathan.testrestfulget.model.okhttp

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request

private const val TAG = "TRST: OkHttpClientUtil"

class OkHttpClientUtil {
    fun fetchUrl(url: String): String {
        Log.d(TAG, "fetchUrl: url=[$url]")

        val request = Request.Builder()
            .url(url)
            .build()

        // Initialize OkHttpClient
        val okHttpClient = OkHttpClient()

        okHttpClient.newCall(request).execute().use { response ->
            return if (response.isSuccessful) {
                response.body?.string() ?: "Empty response"
            } else {
                "Request failed: ${response.message}"
            }
        }
    }
}

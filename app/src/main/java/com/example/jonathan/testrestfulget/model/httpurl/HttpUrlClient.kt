package com.example.jonathan.testrestfulget.model.httpurl

import android.util.Log
import com.example.jonathan.testrestfulget.model.completeUrl
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection

private const val TAG = "TRST: HttpUrlClient"

// Data from web by HttpURLConnection
class HttpUrlClient {
    // This is based on internet search results:
    fun fetchData(): String {
        Log.d(TAG, "fetchData")

        val response = StringBuilder()

        val connection = completeUrl.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            Log.v(TAG, "fetchData: HTTP_OK")

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }

            reader.close()

            Log.v(TAG, "fetchData: response=\n$response")
        } else {
            Log.v(TAG, "fetchData: responseCode=\n${connection.responseCode}")
        }

        connection.disconnect()

        return response.toString()
    }
}

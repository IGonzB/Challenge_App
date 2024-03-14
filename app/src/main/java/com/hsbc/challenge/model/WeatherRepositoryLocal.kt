package com.hsbc.challenge.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hsbc.challenge.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

class WeatherRepositoryLocal(private val context: Context) : WeatherRepository {
    override suspend fun getWeatherData(): Resource<WeatherResponse> = withContext(Dispatchers.IO) {
        try {

            val inputStream: InputStream = context.assets.open("weather.json")
            val inputString = inputStream.bufferedReader().use { it.readText() }
            val data: WeatherResponse = Gson().fromJson(
                inputString,
                object : TypeToken<WeatherResponse?>() {}.type
            )
            Resource.Success(data = data)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}
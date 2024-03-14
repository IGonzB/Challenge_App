package com.hsbc.challenge.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hsbc.challenge.util.common.Resource
import com.hsbc.challenge.util.common.extensions.toErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.InputStream

class WeatherRepositoryLocal(private val context: Context) : WeatherRepository {

    override fun getWeatherData() = flow {
        try {
            val inputStream: InputStream = context.assets.open("weather.json")
            val inputString = inputStream.bufferedReader().use { it.readText() }
            val data: WeatherResponse = Gson().fromJson(
                inputString,
                object : TypeToken<WeatherResponse?>() {}.type
            )
            emit(Resource.Success(data = data))

        } catch (e: Exception) {
            emit(Resource.Error(e.toErrorType()))
        }
    }.flowOn(Dispatchers.IO)
}
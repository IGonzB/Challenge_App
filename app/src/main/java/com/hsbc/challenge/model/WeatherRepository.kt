package com.hsbc.challenge.model

import com.hsbc.challenge.util.Resource

interface WeatherRepository {
    suspend fun getWeatherData(): Resource<WeatherResponse>
}
package com.hsbc.challenge.model

import com.hsbc.challenge.util.common.Resource

interface WeatherRepository {
    suspend fun getWeatherData(): Resource<WeatherResponse>
}
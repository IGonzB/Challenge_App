package com.hsbc.challenge.model

import com.hsbc.challenge.util.common.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherData(): Flow<Resource<WeatherResponse>>
}
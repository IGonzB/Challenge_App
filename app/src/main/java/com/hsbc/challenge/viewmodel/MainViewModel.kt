package com.hsbc.challenge.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsbc.challenge.model.Data
import com.hsbc.challenge.model.WeatherRepository
import com.hsbc.challenge.model.WeatherUnitType
import com.hsbc.challenge.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val _data = MutableStateFlow(Data("", 0, WeatherUnitType.C))
    val weatherInfo = _data.asStateFlow()

    fun requestNextWeatherInfo() {
        viewModelScope.launch {

            when (val weatherData = weatherRepository.getWeatherData()) {

                is Resource.Success -> {
                    weatherData.data?.temperature?.data.orEmpty().random().let {
                        _data.value = it
                    }
                }

                is Resource.Error -> {

                }
            }
        }
    }
}
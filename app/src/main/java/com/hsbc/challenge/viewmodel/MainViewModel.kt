package com.hsbc.challenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsbc.challenge.model.Data
import com.hsbc.challenge.model.WeatherRepository
import com.hsbc.challenge.util.common.ErrorTypeToErrorTextConverter
import com.hsbc.challenge.util.common.Resource
import com.hsbc.challenge.util.common.UiDataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val errorTypeToErrorTextConverter: ErrorTypeToErrorTextConverter
) :
    ViewModel() {

    private val _data = MutableStateFlow<UiDataState<Data>>(UiDataState.Initial())
    val weatherInfo: StateFlow<UiDataState<Data>> = _data.asStateFlow()

    fun requestNextWeatherInfo() {
        viewModelScope.launch {
            weatherRepository.getWeatherData()
                .catch { }
                .collect {
                    when (it) {
                        is Resource.Success -> _data.value =
                            UiDataState.Loaded(it.data.temperature.data.random())

                        is Resource.Error -> _data.value =
                            UiDataState.Error(errorTypeToErrorTextConverter.convert(it.error))
                    }
                }
        }
    }
}
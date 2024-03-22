package com.hsbc.challenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsbc.challenge.model.Data
import com.hsbc.challenge.model.WeatherRepository
import com.hsbc.challenge.util.common.ErrorTypeToErrorTextConverter
import com.hsbc.challenge.util.common.Resource
import com.hsbc.challenge.util.common.UiDataState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val errorTypeToErrorTextConverter: ErrorTypeToErrorTextConverter
) :
    ViewModel() {

    val userIntent = Channel<HomeIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<UiDataState<Data>>(UiDataState.Idle())
    val state: StateFlow<UiDataState<Data>>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is HomeIntent.FetchNextWeather -> requestNextWeatherInfo()
                }
            }
        }
    }

    fun requestNextWeatherInfo() {
        viewModelScope.launch {

            _state.value = UiDataState.Loading()

            when(val resourceData = weatherRepository.getWeatherData()) {
                is Resource.Success -> {
                    _state.value = UiDataState.Loaded(resourceData.data.temperature.data.random())
                }
                is Resource.Error -> {
                    _state.value = UiDataState.Error(errorTypeToErrorTextConverter.convert(resourceData.error))
                }
            }
        }
    }
}
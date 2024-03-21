package com.hsbc.challenge.viewmodel

sealed class HomeIntent {
    object FetchNextWeather : HomeIntent()
}

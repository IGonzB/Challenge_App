package com.hsbc.challenge.model

data class WeatherResponse(
    val temperature: Temperature,
    val recordTime: String
)

data class Temperature(
    val data: List<Data>,
    val recordTime: String
)

data class Data(
    val place: String,
    val value: Int,
    val unit: WeatherUnitType
)

enum class WeatherUnitType {
    C {
        override fun getLabel(): String {
            return "Celcius"
        }
    },
    F {
        override fun getLabel(): String {
            return "Fahrenheit"
        }
    };

    abstract fun getLabel(): String
}
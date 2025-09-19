package com.example.finalassignment.data.remote

import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query


@Serializable
data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>
) {
    @Serializable
    data class Main(
        val temp: Double
    )

    @Serializable
    data class Weather(
        val description: String
    )
}


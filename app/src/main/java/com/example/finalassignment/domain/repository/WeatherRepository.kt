package com.example.finalassignment.domain.repository

import com.example.finalassignment.data.remote.WeatherApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi
) {
    fun getWeather(city: String, apiKey: String): Flow<String> = flow {
        val response = api.getWeather(city, apiKey)
        val description = response.weather.firstOrNull()?.description ?: "Unknown"
        val temp = response.main.temp
        emit("${description.replaceFirstChar { it.uppercase() }}, ${temp}°C")
    }
}
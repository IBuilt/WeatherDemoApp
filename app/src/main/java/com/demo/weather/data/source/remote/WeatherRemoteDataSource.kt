package com.demo.weather.data.source.remote

import com.demo.lib.remote.fetchResult
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(private val weatherApi: WeatherApi) {

    suspend fun fetchCurrentWeather(lat: Double, lng: Double) =
        fetchResult { weatherApi.fetchWeathers(lat, lng) }
}
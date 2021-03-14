package com.demo.weather.data.source.remote

import com.demo.weather.BuildConfig
import com.demo.weather.data.entities.current.CurrentWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun fetchWeathers(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = BuildConfig.WEB_API_KEY,
        @Query("units") units: String = "metric"
    ): Response<CurrentWeather>


    @GET("data/2.5/forecast")
    suspend fun fetchFiveDayWeathers(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = BuildConfig.WEB_API_KEY,
        @Query("units") units: String = "metric"
    ): Response<CurrentWeather>
}
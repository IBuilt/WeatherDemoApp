package com.demo.weather.data.repository

import com.demo.lib.remote.fetchAndSaveOperation
import com.demo.weather.data.entities.current.CityInfo
import com.demo.weather.data.source.local.CityAndWeatherDao
import com.demo.weather.data.source.remote.WeatherRemoteDataSource
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class WeatherRepository @Inject constructor(
    private val weatherDataSource: WeatherRemoteDataSource,
    private val weatherWithCityDao: CityAndWeatherDao
) {

    fun findWeatherByNewCity(context: CoroutineContext, city: CityInfo) =

        fetchAndSaveOperation(

            context = context,


            networkCall = { weatherDataSource.fetchCurrentWeather(city.lat, city.lng) },


            saveCallResult = { currentWeather ->
                weatherWithCityDao.insertCityAndWeather(city, currentWeather)
            }
        )


    fun findAllWeathers() = weatherWithCityDao.findAll()


    fun deleteCity(city: CityInfo) {
        weatherWithCityDao.deleteCity(city)
    }
}
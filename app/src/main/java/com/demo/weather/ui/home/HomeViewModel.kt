package com.demo.weather.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.weather.data.entities.current.CityInfo
import com.demo.weather.data.repository.WeatherRepository

class HomeViewModel @ViewModelInject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {


    fun findAll() = weatherRepository.findAllWeathers()


    fun findWeatherByNewCity(city: CityInfo) =
        weatherRepository.findWeatherByNewCity(viewModelScope.coroutineContext, city)


    fun generateNewCity(cityName: String, lat: Double, lng: Double) =
        CityInfo(name = cityName, lat = lat, lng = lng)


    fun deleteCity(city: CityInfo) {
        weatherRepository.deleteCity(city)
    }
}
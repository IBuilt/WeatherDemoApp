package com.demo.weather.listener

import com.demo.weather.data.entities.current.CityInfo

interface IFragmentCommunicator {
    fun onShowForecast(city: CityInfo)
}
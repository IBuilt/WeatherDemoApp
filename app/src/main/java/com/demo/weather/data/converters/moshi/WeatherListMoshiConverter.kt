package com.demo.weather.data.converters.moshi

import com.demo.weather.data.entities.common.Weather
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class WeatherListMoshiConverter {

    @ToJson
    fun arrayListToJson(list: ArrayList<Weather>): List<Weather> = list

    @FromJson
    fun arrayListFromJson(list: List<Weather>): ArrayList<Weather> = ArrayList(list)
}
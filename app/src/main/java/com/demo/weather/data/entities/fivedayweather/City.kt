package com.demo.weather.data.entities.fivedayweather

import com.demo.weather.data.entities.common.Coord

data class City (
    var id: Long,
    var name: String,
    var coord: Coord,
    var country: String,
    var population: Long,
    var timezone: Long,
    var sunrise: Long,
    var sunset: Long
)
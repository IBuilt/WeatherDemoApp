package com.demo.weather.data.entities.common

data class Weather(
    var id: Long,
    var main: String,
    var description: String,
    var icon: String
)
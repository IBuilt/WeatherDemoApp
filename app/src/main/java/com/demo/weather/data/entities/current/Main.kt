package com.demo.weather.data.entities.current

import com.squareup.moshi.Json

data class Main(

    var temp: Double = 0.0,

    @field:Json(name = "feels_like")
    @Json(name = "feels_like")
    var feelsLike: Double = 0.0,

    @field:Json(name = "temp_min")
    @Json(name = "temp_min")
    var tempMin: Double = 0.0,

    @field:Json(name = "temp_max")
    @Json(name = "temp_max")
    var tempMax: Double = 0.0,

    var pressure: Long = 0L,
    var humidity: Long = 0L,

    @field:Json(name = "sea_level")
    @Json(name = "sea_level")
    var seaLevel: Long = 0L,

    @field:Json(name = "grnd_level")
    @Json(name = "grnd_level")
    var grndLevel: Long = 0L
)
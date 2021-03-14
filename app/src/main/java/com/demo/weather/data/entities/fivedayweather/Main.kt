package com.demo.weather.data.entities.fivedayweather

import com.squareup.moshi.Json

data class Main(

    var temp: Double = 0.0,

    @Json(name = "feels_like")
    @field:Json(name = "feels_like")
    var feelsLike: Double = 0.0,

    @Json(name = "temp_min")
    @field:Json(name = "temp_min")
    var tempMin: Double = 0.0,

    @Json(name = "temp_max")
    @field:Json(name = "temp_max")
    var tempMax: Double = 0.0,

    var pressure: Long = 0L,

    @Json(name = "sea_level")
    @field:Json(name = "sea_level")
    var seaLevel: Long = 0L,

    @Json(name = "grnd_level")
    @field:Json(name = "grnd_level")
    var grndLevel: Long = 0L,

    var humidity: Long = 0L,

    @Json(name = "temp_kf")
    @field:Json(name = "temp_kf")
    var tempKf: Double = 0.0
)


package com.demo.weather.data.entities.fivedayweather

import com.demo.weather.data.entities.common.Clouds
import com.demo.weather.data.entities.common.Weather
import com.demo.weather.data.entities.common.Wind
import com.squareup.moshi.Json

data class ItemHourly(

    var dt: Long,

    var main: Main? = null,

    @field:Json(name = "weather")
    @Json(name = "weather")
    var weathers: ArrayList<Weather> = ArrayList(),

    var clouds: Clouds? = null,

    var wind: Wind? = null,

    var visibility: Long,

    var pop: Double,

    var sys: Sys? = null,

    @Json(name = "dt_txt")
    @field:Json(name = "dt_txt")
    var dtTxt: String,

    var rain: Rain? = null
)
package com.demo.weather.data.entities.current

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.weather.data.entities.common.Clouds
import com.demo.weather.data.entities.common.Coord
import com.demo.weather.data.entities.common.Weather
import com.demo.weather.data.entities.common.Wind
import com.squareup.moshi.Json

@Entity
data class CurrentWeather(

    var coord: Coord? = null,

    @field:Json(name = "weather")
    @Json(name = "weather")
    var weathers: ArrayList<Weather> = ArrayList(),

    var base: String,

    var main: Main? = null,

    var visibility: Long,

    var wind: Wind? = null,

    var clouds: Clouds? = null,

    var dt: Long,

    var sys: Sys? = null,

    var timezone: Long,

    @PrimaryKey
    var id: Long,

    var name: String,

    var cod: Long,

    var cityId: Int = 0
)









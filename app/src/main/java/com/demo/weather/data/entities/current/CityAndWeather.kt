package com.demo.weather.data.entities.current

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class CityAndWeather(

    @Embedded
    var city: CityInfo? = null,

    @Relation(
        parentColumn = "id",
        entityColumn = "cityId"
    )
    var currentWeather: CurrentWeather? = null
)
package com.demo.weather.data.entities.fivedayweather

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FiveDayForecast")
data class FiveDayForecast(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var cod: String,

    var message: Long,

    var cnt: Long,

    var list: ArrayList<ItemHourly> = ArrayList(),

    var city: City? = null,

    var cityId: Int = 0
)
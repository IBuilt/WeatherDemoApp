package com.demo.weather.data.entities.current

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "CityInfo", indices = [Index(value = ["name"], unique = true)])
data class CityInfo(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String = "",

    var lat: Double,

    var lng: Double
)
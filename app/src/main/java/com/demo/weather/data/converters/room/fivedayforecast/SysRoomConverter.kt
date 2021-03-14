package com.demo.weather.data.converters.room.fivedayforecast

import androidx.room.TypeConverter
import com.demo.lib.utils.extensions.toJson
import com.demo.lib.utils.extensions.toJsonObject
import com.demo.lib.utils.extensions.whenNotNull
import com.demo.weather.data.entities.fivedayweather.Sys

class SysRoomConverter {

    @TypeConverter
    fun fromString(value: String): Sys? {
        return value.toJsonObject()
    }


    @TypeConverter
    fun fromArray(value: Sys?): String? {

        whenNotNull(value) {
            return value.toJson()
        }

        return ""
    }
}
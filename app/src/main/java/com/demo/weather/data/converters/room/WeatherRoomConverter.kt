package com.demo.weather.data.converters.room

import androidx.room.TypeConverter
import com.demo.lib.utils.extensions.toJson
import com.demo.lib.utils.extensions.toJsonObject
import com.demo.lib.utils.extensions.whenNotNull
import com.demo.weather.data.entities.common.Weather

class WeatherRoomConverter {

    @TypeConverter
    fun fromString(value: String): Weather? {
        return value.toJsonObject()
    }


    @TypeConverter
    fun fromArray(value: Weather?): String? {

        whenNotNull(value) {
            return value.toJson()
        }

        return ""
    }
}
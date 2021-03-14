package com.demo.weather.data.converters.room.fivedayforecast

import androidx.room.TypeConverter
import com.demo.lib.utils.extensions.toJson
import com.demo.lib.utils.extensions.toJsonObject
import com.demo.lib.utils.extensions.whenNotNull
import com.demo.weather.data.entities.fivedayweather.Main

class MainRoomConverter {

    @TypeConverter
    fun fromString(value: String): Main? {
        return value.toJsonObject()
    }


    @TypeConverter
    fun fromArray(value: Main?): String? {

        whenNotNull(value) {
            return value.toJson()
        }

        return ""
    }
}
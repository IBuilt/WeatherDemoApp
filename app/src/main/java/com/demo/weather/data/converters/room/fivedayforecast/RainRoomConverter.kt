package com.demo.weather.data.converters.room.fivedayforecast

import androidx.room.TypeConverter
import com.demo.lib.utils.extensions.toJson
import com.demo.lib.utils.extensions.toJsonObject
import com.demo.lib.utils.extensions.whenNotNull
import com.demo.weather.data.entities.fivedayweather.Rain

class RainRoomConverter {

    @TypeConverter
    fun fromString(value: String): Rain? {
        return value.toJsonObject()
    }


    @TypeConverter
    fun fromArray(value: Rain?): String? {

        whenNotNull(value) {
            return value.toJson()
        }

        return ""
    }
}
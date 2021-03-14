package com.demo.weather.data.converters.room

import androidx.room.TypeConverter
import com.demo.lib.utils.extensions.toJson
import com.demo.lib.utils.extensions.toJsonList
import com.demo.lib.utils.extensions.whenNotNull
import com.demo.weather.data.entities.common.Weather

class WeatherListRoomConverter {

    @TypeConverter
    fun fromString(value: String): ArrayList<Weather>? {

        whenNotNull(value) {
            return ArrayList(value.toJsonList())
        }

        return ArrayList()
    }


    @TypeConverter
    fun fromArray(list: ArrayList<Weather>): String? {

        whenNotNull(list) {
            return list.toJson()
        }

        return ""
    }
}




package com.demo.weather.data.converters.room

import androidx.room.TypeConverter
import com.demo.lib.utils.extensions.toJson
import com.demo.lib.utils.extensions.toJsonObject
import com.demo.lib.utils.extensions.whenNotNull
import com.demo.weather.data.entities.common.Coord

class CoordRoomConverter {

    @TypeConverter
    fun fromString(value: String): Coord? {
        return value.toJsonObject()
    }


    @TypeConverter
    fun fromArray(value: Coord?): String? {

        whenNotNull(value) {
            return value.toJson()
        }

        return ""
    }
}
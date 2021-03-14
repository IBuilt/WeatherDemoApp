package com.demo.weather.data.converters.room

import androidx.room.TypeConverter
import com.demo.lib.utils.extensions.toJson
import com.demo.lib.utils.extensions.toJsonObject
import com.demo.lib.utils.extensions.whenNotNull
import com.demo.weather.data.entities.common.Clouds

class CloudsRoomConverter {

    @TypeConverter
    fun fromString(value: String): Clouds? {
        return value.toJsonObject()
    }


    @TypeConverter
    fun fromArray(value: Clouds?): String? {

        whenNotNull(value) {
            return value.toJson()
        }

        return ""
    }
}
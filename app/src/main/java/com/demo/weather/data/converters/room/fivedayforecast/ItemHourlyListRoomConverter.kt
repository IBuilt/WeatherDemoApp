package com.demo.weather.data.converters.room.fivedayforecast

import androidx.room.TypeConverter
import com.demo.lib.utils.extensions.toJson
import com.demo.lib.utils.extensions.toJsonList
import com.demo.lib.utils.extensions.whenNotNull
import com.demo.weather.data.entities.fivedayweather.ItemHourly


class ItemHourlyListRoomConverter {

    @TypeConverter
    fun fromString(value: String): ArrayList<ItemHourly>? {

        whenNotNull(value) {
            return ArrayList(value.toJsonList())
        }

        return ArrayList()
    }


    @TypeConverter
    fun fromArray(list: ArrayList<ItemHourly>): String? {

        whenNotNull(list) {
            return list.toJson()
        }

        return ""
    }
}




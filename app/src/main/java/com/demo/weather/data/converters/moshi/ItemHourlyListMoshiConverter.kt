package com.demo.weather.data.converters.moshi

import com.demo.weather.data.entities.fivedayweather.ItemHourly
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ItemHourlyListMoshiConverter {

    @ToJson
    fun arrayListToJson(list: ArrayList<ItemHourly>): List<ItemHourly> = list

    @FromJson
    fun arrayListFromJson(list: List<ItemHourly>): ArrayList<ItemHourly> = ArrayList(list)
}
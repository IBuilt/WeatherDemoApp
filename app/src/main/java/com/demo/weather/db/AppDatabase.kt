package com.demo.weather.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.weather.data.converters.room.*
import com.demo.weather.data.converters.room.current.MainRoomConverter
import com.demo.weather.data.converters.room.current.SysRoomConverter
import com.demo.weather.data.converters.room.fivedayforecast.ItemHourlyListRoomConverter
import com.demo.weather.data.converters.room.fivedayforecast.RainRoomConverter
import com.demo.weather.data.entities.current.CityInfo
import com.demo.weather.data.entities.current.CurrentWeather
import com.demo.weather.data.source.local.CityAndWeatherDao

@Database(
    entities = [CurrentWeather::class, CityInfo::class],
    version = 1, exportSchema = false
)


@TypeConverters(
    CloudsRoomConverter::class,
    CoordRoomConverter::class,
    MainRoomConverter::class,
    SysRoomConverter::class,
    WeatherRoomConverter::class,
    WeatherListRoomConverter::class,
    WindRoomConverter::class,
    com.demo.weather.data.converters.room.fivedayforecast.SysRoomConverter::class,
    RainRoomConverter::class,
    com.demo.weather.data.converters.room.fivedayforecast.MainRoomConverter::class,
    ItemHourlyListRoomConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityAndWeatherDao(): CityAndWeatherDao
}
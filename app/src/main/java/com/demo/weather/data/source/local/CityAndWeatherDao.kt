package com.demo.weather.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.demo.weather.data.entities.current.CityInfo
import com.demo.weather.data.entities.current.CityAndWeather
import com.demo.weather.data.entities.current.CurrentWeather

@Dao
interface CityAndWeatherDao {

    @Transaction
    @Query("Select * From CityInfo")
    fun findAll(): LiveData<List<CityAndWeather>>


    fun insertCityAndWeather(city: CityInfo, currentWeather: CurrentWeather) {

        val rowId = insertCity(city)

        currentWeather.cityId = rowId.toInt()

        insertCurrentWeather(currentWeather)
    }


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentWeather(currentWeather: CurrentWeather)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: CityInfo): Long


    @Delete
    fun deleteCity(city: CityInfo)
}
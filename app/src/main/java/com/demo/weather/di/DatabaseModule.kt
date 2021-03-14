package com.demo.weather.di

import android.content.Context
import androidx.room.Room
import com.demo.weather.BuildConfig
import com.demo.weather.data.repository.WeatherRepository
import com.demo.weather.data.source.local.CityAndWeatherDao
import com.demo.weather.data.source.remote.WeatherRemoteDataSource
import com.demo.weather.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, BuildConfig.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    fun provideCityAndWeatherDao(appDatabase: AppDatabase) =
        appDatabase.cityAndWeatherDao()


    @Provides
    fun provideCityAndWeatherRepo(
        weatherDataSource: WeatherRemoteDataSource,
        cityDao: CityAndWeatherDao
    ) =
        WeatherRepository(weatherDataSource, cityDao)
}
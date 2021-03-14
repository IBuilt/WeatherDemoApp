package com.demo.weather.di

import com.demo.lib.utils.DefaultInterceptor
import com.demo.weather.BuildConfig
import com.demo.weather.constants.Constant
import com.demo.weather.data.converters.moshi.ItemHourlyListMoshiConverter
import com.demo.weather.data.converters.moshi.WeatherListMoshiConverter
import com.demo.weather.data.source.remote.WeatherApi
import com.demo.weather.data.source.remote.WeatherRemoteDataSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(WeatherListMoshiConverter())
            .add(ItemHourlyListMoshiConverter())
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofitClient(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.WEB_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(DefaultInterceptor())
            .connectTimeout(Constant.Network.TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constant.Network.TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constant.Network.TIMEOUT, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    fun provideWeatherRemoteDataSource(weatherApi: WeatherApi) =
        WeatherRemoteDataSource(weatherApi)

}
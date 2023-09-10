package com.demo.weather.history.di

import android.content.Context
import androidx.room.Room
import com.demo.weather.history.io.HistoryDatabase
import com.demo.weather.history.io.WeatherHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesHistoryDatabase(@ApplicationContext context: Context): HistoryDatabase =
        Room.databaseBuilder(context, HistoryDatabase::class.java, HistoryDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesFiveDayHistoryDao(db: HistoryDatabase): WeatherHistoryDao = db.weatherHistoryDao()

}
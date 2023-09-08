package com.demo.weather.history.di

import android.app.Application
import androidx.room.Room
import com.demo.weather.history.io.FiveDayHistoryDao
import com.demo.weather.history.io.HistoryDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesHistoryDatabase(app: Application): HistoryDataBase =
        Room.databaseBuilder(app, HistoryDataBase::class.java, HistoryDataBase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesFiveDayHistoryDao(db: HistoryDataBase): FiveDayHistoryDao = db.fiveDayHistoryDao()

}
package com.demo.weather.history.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

//    @Provides
//    @Singleton
//    fun providesHistoryDatabase(app: Application): HistoryDataBase =
//        Room.databaseBuilder(app, HistoryDataBase::class.java, HistoryDataBase.DB_NAME)
//            .fallbackToDestructiveMigration()
//            .build()
//
//    @Provides
//    @Singleton
//    fun providesFiveDayHistoryDao(db: HistoryDataBase): FiveDayHistoryDao = db.fiveDayHistoryDao()

}
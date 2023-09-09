package com.demo.weather.history.io

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FiveDayWeather::class], version = 1)
abstract class HistoryDataBase: RoomDatabase() {
    abstract fun fiveDayHistoryDao(): FiveDayHistoryDao

    companion object {
        const val DB_NAME = "history_db"
    }
}
package com.demo.weather.history.io

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.weather.common.helper.Converters
import com.demo.weather.history.data.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class HistoryDataBase: RoomDatabase() {
    abstract fun fiveDayHistoryDao(): WeatherHistoryDao

    companion object {
        const val DB_NAME = "history_db"
    }
}
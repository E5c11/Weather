package com.demo.weather.history.io

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.weather.history.data.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: WeatherEntity): Long

    @Query("SELECT * FROM weather_history_table WHERE station = :station")
    suspend fun fetchByStation(station: String): WeatherEntity?

    @Query("SELECT * FROM weather_history_table")
    fun fetchAll(): Flow<List<WeatherEntity>>
}
package com.demo.weather.history.io

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.weather.weather.data.entity.FiveDayWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface FiveDayHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: FiveDayWeather): Long

    @Query("SELECT * FROM five_day_table")
    fun fetchAll(): Flow<List<FiveDayWeather>>
}
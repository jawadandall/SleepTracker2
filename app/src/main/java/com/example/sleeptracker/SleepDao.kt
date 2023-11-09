package com.example.sleeptracker

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface SleepDao {
    @Insert
    fun insertSleep(sleep: List<SleepEntity>)
    @Query("SELECT * FROM sleep_entries")
    fun getAllSleepEntries(): Flow<List<SleepEntity>>


    @Delete
    fun deleteSleep(sleep: SleepEntity)
}

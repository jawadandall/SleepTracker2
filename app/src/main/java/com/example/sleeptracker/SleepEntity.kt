package com.example.sleeptracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_entries") // Room annotation to define this class as an entity
data class SleepEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Primary key field with auto-generation
    val date: String,
    val hours: String,
    val quality: String
)

package com.example.sleeptracker

import android.app.Application
import com.example.sleeptracker.AppDatabase

class SleepApplication : Application() {
    val db by lazy { AppDatabase.getDatabase(this) }
}

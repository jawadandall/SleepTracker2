package com.example.sleeptracker

import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var entries: MutableList<SleepEntity>
    lateinit var sleepDao: SleepDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        entries = mutableListOf()
        sleepDao = AppDatabase.getDatabase(applicationContext).sleepDao()

        val button = findViewById<Button>(R.id.submitBtn)

        val recyclerView: RecyclerView = findViewById(R.id.sleepTrackerRv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = SleepRecyclerViewAdapter(entries)
        recyclerView.adapter = adapter

        val userInputCalendar = findViewById<CalendarView>(R.id.calendarView)
        var userInputCalendarDate = ""

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        userInputCalendarDate = sdf.format(userInputCalendar.date)

        lifecycleScope.launch(Dispatchers.IO) {
            // Retrieve entries using a Flow
            val allEntries = (application as SleepApplication).db.sleepDao().getAllSleepEntries()

            // Update UI on the main thread
            withContext(Dispatchers.Main) {
                allEntries.collect { entriesFromDatabase ->
                    entries.clear()
                    entries.addAll(entriesFromDatabase)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        userInputCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, dayOfMonth)
            userInputCalendarDate = sdf.format(selectedCalendar.time)
        }

        button.setOnClickListener {
            val userSleepQuality = findViewById<Slider>(R.id.qualitySlider).value.toString()
            val userInputHours = findViewById<TextView>(R.id.hoursEditText).text.toString()

            val userEntry = SleepEntity(date = userInputCalendarDate, hours = userInputHours, quality = userSleepQuality)

            // Use coroutine to perform database operation asynchronously
            lifecycleScope.launch(Dispatchers.IO) {

                // Insert the entry into the database
                (application as SleepApplication).db.sleepDao().insertSleep(userEntry)

                // Retrieve entries using a Flow
                val allEntries = (application as SleepApplication).db.sleepDao().getAllSleepEntries()

                // Update UI on the main thread
                allEntries.collect { entriesFromDatabase ->
                    withContext(Dispatchers.Main) {
                        entries.clear()
                        entries.addAll(entriesFromDatabase)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }










            adapter.notifyDataSetChanged()
        }
    }


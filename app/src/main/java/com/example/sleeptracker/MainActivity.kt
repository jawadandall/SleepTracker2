package com.example.sleeptracker

import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var entries: MutableList<SleepEntry>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        entries= mutableListOf() // Initialize the list of items
        val button = findViewById<Button>(R.id.submitBtn)

        val recyclerView: RecyclerView = findViewById(R.id.sleepTrackerRv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = SleepRecyclerViewAdapter(entries)
        recyclerView.adapter = adapter

        // Create a Calendar instance and set the selected date
        val userInputCalendar = findViewById<CalendarView>(R.id.calendarView)
        var userInputCalendarDate =""

        // Format the date as needed (e.g., "dd/MM/yyyy")
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        userInputCalendarDate = sdf.format(userInputCalendar.date)

        //Listener on the Calendar updates date if user changes the date
        userInputCalendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, dayOfMonth)


            userInputCalendarDate = sdf.format(selectedCalendar.time)
        }











// Add the newly created item to the list of items
//Do on button click
        button.setOnClickListener{
            val userSleepQuality= findViewById<Slider>(R.id.qualitySlider).value.toString()
            //val userInputCalendarDate = findViewById<CalendarView>(R.id.calendarView).dateTextAppearance


            //date.toString()
            val userInputhours= findViewById<TextView>(R.id.hoursEditText).text.toString()
            // Create a SleepEntry instance from the collected values
            val userEntry = SleepEntry(userInputCalendarDate, userInputhours,userSleepQuality)

            entries.add(userEntry)
            adapter.notifyDataSetChanged()
        }


// Notify the adapter that the data set has changed
        adapter.notifyDataSetChanged()
    }
}
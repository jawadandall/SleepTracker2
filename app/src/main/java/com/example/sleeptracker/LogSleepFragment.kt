package com.example.sleeptracker

import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.slider.Slider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.Locale


class LogSleepFragment : Fragment() {

    interface onSleepEntryListener{
        fun onSleepEntryAdded(sleepEntry: SleepEntity)
    }
     private var listener: onSleepEntryListener? = null

    override fun onAttach(context: Context){
        super.onAttach(context)
        if(context is onSleepEntryListener){
            listener = context
        } else{
            throw RuntimeException("$context must implement onMoodEntryListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_sleep, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.submitBtn)
        val userInputCalendar = view.findViewById<CalendarView>(R.id.calendarView)
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        var userInputCalendarDate = sdf.format(userInputCalendar.date)

        userInputCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, dayOfMonth)
            userInputCalendarDate = sdf.format(selectedCalendar.time)
        }

        button.setOnClickListener {
            val userSleepQuality = view.findViewById<Slider>(R.id.qualitySlider).value.toString()
            val userInputHours = view.findViewById<TextView>(R.id.hoursEditText).text.toString()

            val userEntry = SleepEntity(
                date = userInputCalendarDate,
                hours = userInputHours,
                quality = userSleepQuality
            )

            listener?.onSleepEntryAdded(userEntry)



        }
    }
}
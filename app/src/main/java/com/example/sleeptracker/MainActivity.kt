package com.example.sleeptracker

import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.slider.Slider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity(), LogSleepFragment.onSleepEntryListener {
    private val sleepEntriesFragment = SleepEntriesFragment()
    private val logSleepFragment = LogSleepFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Set the default fragment to LogSleepFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, logSleepFragment)
            .commit()

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_list -> {
                    replaceFragment(sleepEntriesFragment)
                    true
                }
                R.id.nav_entry -> {
                    replaceFragment(logSleepFragment)
                    true
                }
                else -> false
            }
        }
    }

    // Function to replace fragments
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }



        override fun onSleepEntryAdded(sleepEntry: SleepEntity) {

            lifecycleScope.launch(Dispatchers.IO) {

                // Insert the entry into the database
                (application as SleepApplication).db.sleepDao().insertSleep(sleepEntry)

            }
        }

}


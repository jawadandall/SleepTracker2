package com.example.sleeptracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

lateinit var entries: MutableList<SleepEntity>
lateinit var sleepDao: SleepDao
private lateinit var adapter:SleepRecyclerViewAdapter
class SleepEntriesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sleep_entries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        entries = mutableListOf()
        sleepDao = AppDatabase.getDatabase(requireContext()).sleepDao()

        val recyclerView: RecyclerView = view.findViewById(R.id.sleepTrackerRv)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = SleepRecyclerViewAdapter(entries)
        recyclerView.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            requireActivity().application.let { application ->
                // Retrieve entries using a Flow
                val allEntries =
                    (application as SleepApplication).db.sleepDao().getAllSleepEntries()

                // Update UI on the main thread
                withContext(Dispatchers.Main) {
                    allEntries.collect { entriesFromDatabase ->
                        entries.clear()
                        entries.addAll(entriesFromDatabase)

                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

    fun addSleepEntry(sleepEntry: SleepEntity){
        // Use coroutine to perform database operation asynchronously
        lifecycleScope.launch(Dispatchers.IO) {

            // Insert the entry into the database
            requireActivity().application.let { application ->

                (application as SleepApplication).db.sleepDao().insertSleep(sleepEntry)

                // Retrieve entries using a Flow
                val allEntries =
                    (application as SleepApplication).db.sleepDao().getAllSleepEntries()

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

    }
}

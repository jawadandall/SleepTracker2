package com.example.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SleepRecyclerViewAdapter(private val items: List<SleepEntity>) : RecyclerView.Adapter<SleepRecyclerViewAdapter.MyViewHolder>(){
    // Implement methods for the RecyclerView.Adapter
    // onCreateViewHolder, onBindViewHolder, getItemCount, and ViewHolder class
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // Initialize views inside the item layout
        val qualityTextView : TextView =itemView.findViewById(R.id.QualityTv)
        val dateView : TextView = itemView.findViewById(R.id.dateView)
        val hoursView : TextView = itemView.findViewById(R.id.timeView)
        //val button : Button = itemView.findViewById(R.id.submitBtn)
        // Example: val textView: TextView = itemView.findViewById(R.id.textView)
        // Set values to these views in onBindViewHolder


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.sleep_entry, parent, false)
        // Return a new holder instance
        return MyViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items.get(position)
        // Set item views based on views and data model

        val formattedTime = context.resources.getString(R.string.time, item.hours)
        val formattedDate = context.resources.getString(R.string.date, item.date)
        val formattedQuality = context.resources.getString(R.string.sleep_quality, item.quality)



        holder.qualityTextView.text = formattedQuality
        holder.dateView.text = formattedDate
        holder.hoursView.text = formattedTime
        //holder.dateView.append(item.date)//text= item.date.toString()
        //holder.hoursView.append(item.hours)//text= item.hours
    }
}

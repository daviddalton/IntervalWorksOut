package com.mungomash.intervalworksout.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mungomash.intervalworksout.R
import com.mungomash.intervalworksout.model.Workout


class SingleWorkoutAdapter(private val context: Context, private val workout: Workout): RecyclerView.Adapter<SingleWorkoutAdapter.SingleWorkoutViewHolder>() {

    class SingleWorkoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.exercise_name)
        val duration: TextView = view.findViewById(R.id.duration_text)
        val rest: TextView = view.findViewById(R.id.rest_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleWorkoutViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_card, parent, false)

        return SingleWorkoutViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: SingleWorkoutViewHolder, position: Int) {
        val item = workout.exercises!![position]
        holder.title.text = item.name
        holder.duration.text = (item.duration / 1000).toString() + "s"
        holder.rest.text = "Rest: " + (item.prep / 1000).toString() + "s"
    }

    override fun getItemCount() = workout.exercises!!.size
}
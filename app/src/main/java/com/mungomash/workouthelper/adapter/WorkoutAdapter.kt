package com.mungomash.workouthelper.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mungomash.workouthelper.R
import com.mungomash.workouthelper.model.Workout

class WorkoutAdapter(private val onClickListener: OnClickListener, private val context: Context, private val dataset: List<Workout>) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    class WorkoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.exercise_title)
        val typeTextView: TextView = view.findViewById(R.id.timer)
    }

    class OnClickListener(val clickListener: (item: Workout) -> Unit) {
        fun onClick(item: Workout) = clickListener(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.set_card, parent, false)

        return WorkoutViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.name
        holder.typeTextView.text = context.getText(item.workoutType)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
    }

    override fun getItemCount() = dataset.size

}
package com.mungomash.workouthelper.adapter

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mungomash.workouthelper.R
import com.mungomash.workouthelper.model.Exercise

class WorkingOutAdapter(private val context: Context, private val dataset: List<Exercise>, private val recyclerView: RecyclerView) : RecyclerView.Adapter<WorkingOutAdapter.WorkingOutViewHolder>() {

    class WorkingOutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.exercise_title)
        val timer: TextView = view.findViewById(R.id.timer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkingOutViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercising_view, parent, false)

        return WorkingOutViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: WorkingOutViewHolder, position: Int) {
        val item = dataset[position]
        holder.nameText.text = item.name

        //TODO: make this only count down when you are hovered over the item
        object : CountDownTimer(item.prep, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                holder.timer.text = (millisUntilFinished / 1000).toString()
                //TODO: add a text element that will show we are either doing core/stretch/lift/drill or preping/switch
            }

            override fun onFinish() {
                object : CountDownTimer(item.duration, 1000) {

                    override fun onTick(millisUntilFinished: Long) {
                        holder.timer.text = (millisUntilFinished / 1000).toString()
                    }

                    override fun onFinish() {
                        recyclerView.smoothScrollToPosition(holder.adapterPosition + 1)
                    }
                }.start()
            }
        }.start()
    }

    override fun getItemCount() = dataset.size
}
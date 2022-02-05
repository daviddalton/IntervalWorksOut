package com.mungomash.intervalworksout

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mungomash.intervalworksout.adapter.SingleWorkoutAdapter
import com.mungomash.intervalworksout.data.Datasource
import com.mungomash.intervalworksout.databinding.ActivityWorkoutBinding

class WorkoutActivity: AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("workoutId")

        // Initialize data.
        val data = Datasource().workoutBasedOnId(id!!.toInt())

        val singleWorkoutView = findViewById<RecyclerView>(R.id.exercises_recycler)
        singleWorkoutView.adapter = SingleWorkoutAdapter(this, data)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        singleWorkoutView.setHasFixedSize(true)


        val title = findViewById<TextView>(R.id.workout_name_text)
        val type = findViewById<TextView>(R.id.workout_type_text)

        title.text = data.name
        type.text = this.getText(data.workoutType)

        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener {
            startWorkout(id)
        }
    }

    fun startWorkout(id: String) {
        val intent = Intent(this, WorkingOutActivity::class.java).apply {
            putExtra("workoutId", id)
        }
        startActivity(intent)
    }
}
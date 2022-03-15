package com.mungomash.workouthelper

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.mungomash.workouthelper.adapter.SingleWorkoutAdapter
import com.mungomash.workouthelper.data.Datasource
import com.mungomash.workouthelper.databinding.ActivityWorkoutBinding
import com.mungomash.workouthelper.model.Exercise
import com.mungomash.workouthelper.model.Workout

class WorkoutActivity: AppCompatActivity(), OnSuccessListener<DocumentSnapshot> {

    private lateinit var binding: ActivityWorkoutBinding
    private lateinit var workoutToBeSent: Workout
    private lateinit var exercisesToBeShown: MutableList<Exercise>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("workoutId")

        // Initialize data.
        Datasource().getSingleWorkout(this, id!!, this)

        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener {
            startWorkout(id)
        }
    }

    private fun startWorkout(id: String) {
        val intent = Intent(this, WorkingOutActivity::class.java).apply {
            putExtra("workoutId", workoutToBeSent.id)
        }
        startActivity(intent)
    }

    override fun onSuccess(workout: DocumentSnapshot?) {

        if (workout != null) {
            val exercises: List<String> = workout.data!!["exercises"] as List<String>
            val w = Workout(workout.id, workout.data!!["name"].toString(), exercises)

            workoutToBeSent = w

            for (e in exercises) {
                val result = Datasource().getSingleExercise(this, e)
                if (result != null) {
                    exercisesToBeShown.add(result)
                }

            }

            val singleWorkoutView = findViewById<RecyclerView>(R.id.exercises_recycler)
            singleWorkoutView.adapter = SingleWorkoutAdapter(this, exercisesToBeShown)
            singleWorkoutView.setHasFixedSize(true)

            val title = findViewById<TextView>(R.id.workout_name_text)

            title.text = w.name
        }




    }
}
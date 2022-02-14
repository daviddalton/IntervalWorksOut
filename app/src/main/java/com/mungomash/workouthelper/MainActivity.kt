package com.mungomash.workouthelper

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mungomash.workouthelper.adapter.WorkoutAdapter
import com.mungomash.workouthelper.data.Datasource
import com.mungomash.workouthelper.databinding.ActivityMainBinding
import com.mungomash.workouthelper.model.Workout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("userId")

        // Initialize data.
        val myDataset = Datasource().loadSets()

        //val data = Datasource().getAllWorkouts(this)

        val user = FirebaseAuth.getInstance().currentUser
        // Datasource().getAllExercises(this)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = WorkoutAdapter(WorkoutAdapter.OnClickListener { item ->
            handleClick(item)
        }, this, myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)
    }

    private fun handleClick(item: Workout) {
        if (item.id != "9999") {
            val intent = Intent(this, WorkoutActivity::class.java).apply {
                putExtra("workoutId", item.id)
            }
            startActivity(intent)
        } else {
            //TODO: replace this with a new activity that enables the ability to actually create a new workout and save it to local storage.
            Toast.makeText(applicationContext, "Creating new workout", Toast.LENGTH_SHORT).show()
        }
    }
}
package com.mungomash.intervalworksout

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mungomash.intervalworksout.adapter.WorkoutAdapter
import com.mungomash.intervalworksout.data.Datasource
import com.mungomash.intervalworksout.databinding.ActivityMainBinding
import com.mungomash.intervalworksout.model.Workout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize data.
        val myDataset = Datasource().loadSets()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = WorkoutAdapter(WorkoutAdapter.OnClickListener { item ->
            handleClick(item)
        }, this, myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)
    }

    fun handleClick(item: Workout) {
        if (item.id != 9999) {
            val intent = Intent(this, WorkoutActivity::class.java).apply {
                putExtra("workoutId", item.id.toString())
            }
            startActivity(intent)
        } else {
            //TODO: replace this with a new activity that enables the ability to actually create a new workout and save it to local storage.
            Toast.makeText(applicationContext, "Creating new workout", Toast.LENGTH_SHORT).show()
        }
    }
}